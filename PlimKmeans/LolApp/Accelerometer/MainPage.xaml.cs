using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using Windows.Devices.Sensors;
using System.Threading.Tasks;

// Pour en savoir plus sur le modèle d'élément Page vierge, consultez la page http://go.microsoft.com/fwlink/?LinkId=391641

namespace Accelerometer
{
    /// <summary>
    /// Une page vide peut être utilisée seule ou constituer une page de destination au sein d'un frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        private Windows.Devices.Sensors.Accelerometer accelSensor;
        private Compass compSensor;
        private Gyrometer gyroSensor;
        private Inclinometer incliSensor;
        private LightSensor lightSensor;
        private bool started = false;
        private List<String> activities = new List<String>();
        private double accelX, accelY, accelZ, comp, gyroX, gyroY, gyroZ, incliYaw, incliPitch, incliRoll, light;
        private DateTime lastTime;
        public MainPage()
        {
            this.InitializeComponent();

            this.NavigationCacheMode = NavigationCacheMode.Required;

            DataEntry.loadAll();

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
                if(lastTime != null)
                {
                    if ((dt - lastTime).Milliseconds > 100)
                    {
                        DataEntry dE = new DataEntry();
                        if (this.activities.Count > 0)
                        {
                            dE.activity = this.activities.ElementAt<string>(this.activities.Count - 1);
                        }
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
                        DataEntry.addEntryToList(dE);
                        lastTime = dt;
                        this.textBlock_logquantity.Text = DataEntry.dataEntries.Count().ToString("0.0");
                    }
                } else
                {
                    lastTime = dt;
                }
            }
         }

        private void button_scan_activity_Click(object sender, RoutedEventArgs e)
        {
            DataEntry.saveAll();
            Frame.Navigate(typeof(Detector));
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

        private void button_Click(object sender, RoutedEventArgs e)
        {
            started = !started;
            if(started)
            {
                this.button_logger.Content = "STOP LOGGING";
            } else
            {
                this.button_logger.Content = "START LOGGING";
            }
        }

        private void button_del_Click(object sender, RoutedEventArgs e)
        {
            if (this.activities.Count > 0)
            {
                String lastTopActivity = this.activities.ElementAt(this.activities.Count - 1);
                this.activities.RemoveAt(this.activities.Count - 1);
                this.textBox_activity.Text = lastTopActivity;

                if (this.activities.Count > 0)
                {
                    String topActivity = this.activities.ElementAt(this.activities.Count - 1);
                    this.label_top_activity.Text = "- " + topActivity + " -";
                } else
                {
                    this.label_top_activity.Text = "";
                }
            }
        }

        private void button_add_Click(object sender, RoutedEventArgs e)
        {
            String topActivity = this.textBox_activity.Text;
            this.label_top_activity.Text = "- " + topActivity + " -";
            this.activities.Add(topActivity);
            this.textBox_activity.Text = "select activity ...";
        }

        private void button_kmeans_editor_Click(object sender, RoutedEventArgs e)
        {
            DataEntry.saveAll();
            Frame.Navigate(typeof(KMeansEditor));
        }
    }
}
