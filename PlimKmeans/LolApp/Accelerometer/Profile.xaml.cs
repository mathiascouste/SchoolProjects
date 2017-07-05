using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// Pour en savoir plus sur le modèle d’élément Page vierge, consultez la page http://go.microsoft.com/fwlink/?LinkID=390556

namespace Accelerometer
{
    /// <summary>
    /// Une page vide peut être utilisée seule ou constituer une page de destination au sein d'un frame.
    /// </summary>
    public sealed partial class Profile : Page
    {
        private Dictionary<int, List<DataEntry>> partoches = new Dictionary<int, List<DataEntry>>();
        private int nPartoches;
        private int show = 0;

        private List<TextBlock> tab = new List<TextBlock>();

        public Profile()
        {
            this.InitializeComponent();

            loadPartitions();
            drawImage();
        }

        private void clearTab()
        {
            for (int i = 0; i < tab.Count(); i++)
            {
                this.drawing.Children.Remove(tab[i]);
            }
            tab.Clear();
        }

        private void writeTab(int index, string text, int x, int y, int angle)
        {
            TextBlock tb = new TextBlock();
            tb.Text = text;
            tb.Margin = new Thickness(x, y, 0, 0);
            RotateTransform rT = new RotateTransform();
            rT.Angle = angle;
            tb.RenderTransform = rT;

            this.drawing.Children.Add(tb);
            tab.Add(tb);
        }

        private void loadPartitions()
        {
            List<DataEntry> datas = DataEntry.filtredDatas;
            for(int i = 0; i < datas.Count(); i++)
            {
                DataEntry dE = datas.ElementAt(i);
                int part = dE.partition;
                if(!partoches.ContainsKey(part))
                {
                    partoches.Add(part, new List<DataEntry>());
                }
                partoches[part].Add(dE);
            }
            nPartoches = partoches.Count();
        }

        private void drawImage()
        {
            if(!partoches.ContainsKey(show))
            {
                return;
            }
            clearTab();
            this.text_index.Text = (show + 1).ToString("0") + "/" + nPartoches;
            List<DataEntry> dEs = partoches[show];
            Dictionary<string, double> prop = new Dictionary<string, double>();
            for (int i = 0; i < dEs.Count(); i++)
            {
                string act = dEs.ElementAt(i).activity;
                if (!prop.ContainsKey(act))
                {
                    prop.Add(act, 1);
                }
                prop[act] = prop[act] + 1;
            }
            int count = prop.Count();
            double sum = 0;
            for (int i = 0; i < count; i++)
            {
                sum += prop.ElementAt(i).Value;
            }
            for (int i = 0; i < count; i++)
            {
                prop[prop.ElementAt(i).Key] /= sum;
            }

            WriteableBitmap wB = BitmapFactory.New(300, 300);

            int x0 = 150, y0 = 150;
            int wl = 150;
            double currentAngle = 0;

            wB.DrawEllipse(0,0,2*x0, 2*y0, Colors.White);
            

            for (int i = 0; i < count; i++)
            {
                string activity = prop.ElementAt(i).Key;
                double val = prop.ElementAt(i).Value;

                int x1 = (int)( Math.Cos(currentAngle) * wl);
                int y1 = (int)( Math.Sin(currentAngle) * wl);

                currentAngle += Math.PI * val;
                double mid = currentAngle;
                currentAngle += Math.PI * val;
                int xm = (int)(Math.Cos(mid) * wl);
                int ym = (int)(Math.Sin(mid) * wl);

                int Xmoy = xm / 2 + x0;
                int Ymoy = ym / 2 + y0;

                wB.DrawLine(x0, y0, x0 + x1, y0 + y1, Colors.White);
                writeTab(i, activity, (int)Xmoy, (int)Ymoy, 0/* (int) ( 360.0*mid/(2*Math.PI))*/);
            }


            //Update picture
            wB.Invalidate();
            ImageControl.Source = wB;
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
            Frame.Navigate(typeof(KMeansEditor));
        }

        private void button_last_Click(object sender, RoutedEventArgs e)
        {
            show--;
            if(show < 0)
            {
                show++;
            }
            drawImage();
        }

        private void button_next_Click(object sender, RoutedEventArgs e)
        {
            show++;
            if (show >= nPartoches)
            {
                show--;
            }
            drawImage();
        }

        private void button_Click(object sender, RoutedEventArgs e)
        {
            for (int k = 0; k < nPartoches; k++) {
                List<DataEntry> dEs = partoches[k];
                Dictionary<string, double> prop = new Dictionary<string, double>();
                for (int i = 0; i < dEs.Count(); i++)
                {
                    string act = dEs.ElementAt(i).activity;
                    if (!prop.ContainsKey(act))
                    {
                        prop.Add(act, 1);
                    }
                    prop[act] = prop[act] + 1;
                }
                int best = 0;
                for(int i = 1; i < prop.Count(); i++)
                {
                    if(prop.ElementAt(i).Value > prop.ElementAt(best).Value)
                    {
                        best = i;
                    }
                }
                ProfileSave.Center center = new ProfileSave.Center();
                center.activity = prop.ElementAt(best).Key;
                System.Diagnostics.Debug.WriteLine("selected activity : " + prop.ElementAt(best).Key + " with : " + prop.ElementAt(best).Value);
                center.center = KmeansMethode.lastMeans[k];
                ProfileSave.getInstance().AddCenter(center);
            }
            ProfileSave.getInstance().setFilter(KMeansEditor.lastFilter);
        }
    }
}
