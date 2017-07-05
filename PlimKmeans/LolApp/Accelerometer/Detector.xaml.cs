using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Devices.Sensors;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// Pour en savoir plus sur le modèle d’élément Page vierge, consultez la page http://go.microsoft.com/fwlink/?LinkID=390556

namespace Accelerometer
{
    /// <summary>
    /// Une page vide peut être utilisée seule ou constituer une page de destination au sein d'un frame.
    /// </summary>
    public sealed partial class Detector : Page
    {
        private Windows.Devices.Sensors.Accelerometer accelSensor;
        private Compass compSensor;
        private Gyrometer gyroSensor;
        private Inclinometer incliSensor;
        private LightSensor lightSensor;
        private bool started = false;
        private double accelX, accelY, accelZ, comp, gyroX, gyroY, gyroZ, incliYaw, incliPitch, incliRoll, light;

        private void button_logger_Click(object sender, RoutedEventArgs e)
        {
            started = !started;
            if(started)
            {
                this.button_logger.Content = "STOP DETECTING";
            } else
            {
                this.button_logger.Content = "START DETECTING";
            }
        }

        private void button_kmeans_logger_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(MainPage));
        }

        private DateTime lastTime;

        private List<DataEntry> fifo = new List<DataEntry>();

        public Detector()
        {
            this.InitializeComponent();

            this.accelSensor = Windows.Devices.Sensors.Accelerometer.GetDefault();
            if (this.accelSensor != null) this.accelSensor.ReadingChanged += new TypedEventHandler<Windows.Devices.Sensors.Accelerometer, AccelerometerReadingChangedEventArgs>(myAccelHandler);

            this.compSensor = Compass.GetDefault();
            if (this.compSensor != null) this.compSensor.ReadingChanged += new TypedEventHandler<Compass, CompassReadingChangedEventArgs>(myCompassHandler);

            this.gyroSensor = Gyrometer.GetDefault();
            if (this.gyroSensor != null) this.gyroSensor.ReadingChanged += new TypedEventHandler<Gyrometer, GyrometerReadingChangedEventArgs>(myGyroHandler);

            this.incliSensor = Inclinometer.GetDefault();
            if (this.incliSensor != null) this.incliSensor.ReadingChanged += new TypedEventHandler<Inclinometer, InclinometerReadingChangedEventArgs>(myIncliHandler);

            this.lightSensor = LightSensor.GetDefault();
            if (this.lightSensor != null) this.lightSensor.ReadingChanged += new TypedEventHandler<LightSensor, LightSensorReadingChangedEventArgs>(myLightHandler);

            accelX = accelY = accelZ = comp = gyroX = gyroY = gyroZ = incliYaw = incliPitch = incliRoll = light = 0;
        }

        private void checkTimeToLog()
        {
            if (started)
            {
                DateTime dt = DateTime.Now;
                if (lastTime != null)
                {
                    if ((dt - lastTime).Milliseconds > 100)
                    {
                        DataEntry dE = new DataEntry();

                        dE.datas.Add("accelX", accelX);
                        dE.datas.Add("accelY", accelY);
                        dE.datas.Add("accelZ", accelY);
                        dE.datas.Add("compass", comp);
                        dE.datas.Add("gyroX", gyroX);
                        dE.datas.Add("gyroY", gyroY);
                        dE.datas.Add("gyroZ", gyroZ);
                        dE.datas.Add("incliYaw", incliYaw);
                        dE.datas.Add("incliPitch", incliPitch);
                        dE.datas.Add("incliRoll", incliRoll);
                        dE.datas.Add("light", light);
                        
                        lastTime = dt;

                        fifo.Add(dE);
                        int framewidth = DataEntry.FRAME_WIDTH;
                        if (fifo.Count()> framewidth)
                        {
                            fifo.RemoveRange(0, fifo.Count() - framewidth);
                            this.textbox_activity.Text = ProfileSave.getInstance().analyse(fifo);
                        }
                    }
                }
                else
                {
                    lastTime = dt;
                }
            }
        }

        public void myIncliHandler(Inclinometer i, InclinometerReadingChangedEventArgs e)
        {
            Windows.ApplicationModel.Core.CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal,
                () =>
                {
                    this.value_incli_yaw.Text = e.Reading.YawDegrees.ToString("0.00");
                    this.incliYaw = e.Reading.YawDegrees;
                    this.value_incli_pitch.Text = e.Reading.PitchDegrees.ToString("0.00");
                    this.incliPitch = e.Reading.PitchDegrees;
                    this.value_incli_roll.Text = e.Reading.RollDegrees.ToString("0.00");
                    this.incliRoll = e.Reading.RollDegrees;
                    checkTimeToLog();
                }
            );
        }

        public void myGyroHandler(Gyrometer g, GyrometerReadingChangedEventArgs e)
        {
            Windows.ApplicationModel.Core.CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal,
                () =>
                {
                    this.value_gyro_x.Text = e.Reading.AngularVelocityX.ToString("0.00");
                    this.gyroX = e.Reading.AngularVelocityX;
                    this.value_gyro_y.Text = e.Reading.AngularVelocityY.ToString("0.00");
                    this.gyroY = e.Reading.AngularVelocityY;
                    this.value_gyro_z.Text = e.Reading.AngularVelocityZ.ToString("0.00");
                    this.gyroZ = e.Reading.AngularVelocityZ;
                    checkTimeToLog();
                }
            );
        }

        public void myLightHandler(LightSensor l, LightSensorReadingChangedEventArgs e)
        {
            Windows.ApplicationModel.Core.CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal,
                () =>
                {
                    this.value_light_lux.Text = e.Reading.IlluminanceInLux.ToString("0.00");
                    this.light = e.Reading.IlluminanceInLux;
                    checkTimeToLog();
                }
            );
        }

        public void myCompassHandler(Compass c, CompassReadingChangedEventArgs e)
        {
            Windows.ApplicationModel.Core.CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal,
                () =>
                {
                    this.value_compass_deg.Text = e.Reading.HeadingMagneticNorth.ToString("0.00");
                    this.comp = e.Reading.HeadingMagneticNorth;
                    checkTimeToLog();
                }
            );
        }

        public void myAccelHandler(Windows.Devices.Sensors.Accelerometer a, AccelerometerReadingChangedEventArgs e)
        {
            Windows.ApplicationModel.Core.CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal,
                () =>
                {
                    this.value_accel_x.Text = e.Reading.AccelerationX.ToString("0.00");
                    this.accelX = e.Reading.AccelerationX;
                    this.value_accel_y.Text = e.Reading.AccelerationY.ToString("0.00");
                    this.accelY = e.Reading.AccelerationY;
                    this.value_accel_z.Text = e.Reading.AccelerationZ.ToString("0.00");
                    this.accelZ = e.Reading.AccelerationZ;
                    checkTimeToLog();
                }
            );
        }

        /// <summary>
        /// Invoqué lorsque cette page est sur le point d'être affichée dans un frame.
        /// </summary>
        /// <param name="e">Données d'événement décrivant la manière dont l'utilisateur a accédé à cette page.
        /// Ce paramètre est généralement utilisé pour configurer la page.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
        }
    }
}
