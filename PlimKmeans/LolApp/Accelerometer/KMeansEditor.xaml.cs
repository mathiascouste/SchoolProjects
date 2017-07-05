using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
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
    public sealed partial class KMeansEditor : Page
    {
        public static List<String> lastFilter;
        private List<String> filter;
        public KMeansEditor()
        {
            filter = new List<string>();
            this.InitializeComponent();
            this.button_profile.IsEnabled = false;
        }

        /// <summary>
        /// Invoqué lorsque cette page est sur le point d'être affichée dans un frame.
        /// </summary>
        /// <param name="e">Données d'événement décrivant la manière dont l'utilisateur a accédé à cette page.
        /// Ce paramètre est généralement utilisé pour configurer la page.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
        }

        private void button_sensor_logger_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(MainPage));
        }

        private void button_start_kmeans_Click_1(object sender, RoutedEventArgs e)
        {
            filter.Clear();
            if (this.checkBox_accel.IsChecked == true)
            {
                filter.Add("accelX");
                filter.Add("accelY");
                filter.Add("accelZ");
            }
            if (this.checkBox_gyro.IsChecked == true)
            {
                filter.Add("gyroX");
                filter.Add("gyroY");
                filter.Add("gyroZ");
            }
            if (this.checkBox_incli.IsChecked == true)
            {
                filter.Add("incliYaw");
                filter.Add("incliPitch");
                filter.Add("incliRoll");
            }
            if (this.checkBox_compass.IsChecked == true)
            {
                filter.Add("compass");
            }
            if (this.checkBox_lightsensor.IsChecked == true)
            {
                filter.Add("light");
            }
            DataEntry.filtredDatas = DataEntry.filterAndPreprossDatas(DataEntry.filtredDatas, filter);

            // call Kmeans here
            int k = 0;
            double eps = 0;
            int.TryParse(this.textBox_k.Text, out k);
            double.TryParse(this.textBox_eps.Text, out eps);

            this.testAsync.Text = "waiting for kmeans";

            KmeansMethode.DoKmean(k, eps);

            this.testAsync.Text = "kmeans over";
            this.button_profile.IsEnabled = true;

        }

        private void button_profile_Click(object sender, RoutedEventArgs e)
        {
            lastFilter = filter;
            Frame.Navigate(typeof(Profile));
        }
    }
}
