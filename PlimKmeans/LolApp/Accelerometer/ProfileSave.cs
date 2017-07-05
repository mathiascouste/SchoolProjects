using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Accelerometer
{
    public class ProfileSave
    {
        public class Center
        {
            public double[] center;
            public string activity;
        }
        private List<Center> centers;
        private List<string> filter;

        private static ProfileSave profile;

        private ProfileSave()
        {
            this.centers = new List<Center>();
            this.filter = new List<string>();
        }

        public static ProfileSave getInstance()
        {
            if(profile== null)
            {
                profile = new ProfileSave();
            }
            return profile;
        }

        internal void AddCenter(Center center)
        {
            this.centers.Add(center);
        }

        internal void setFilter(List<string> filter)
        {
            this.filter.Clear();
            for(int i = 0; i < filter.Count(); i++)
            {
                this.filter.Add(filter.ElementAt(i));
            }
        }

        internal string analyse(List<DataEntry> fifo)
        {
            List<DataEntry> filteredFifoMSE = DataEntry.filterAndPreprossDatas(fifo, filter);
            System.Diagnostics.Debug.WriteLine("MSE returns " + filteredFifoMSE.Count() + " dateEntry");
            DataEntry dE = filteredFifoMSE.ElementAt(0);
            double[] values = dE.getRawDatas();

            double best = -1;
            double bestDistance = 0;
            string bestActivity = "not found";
            for(int i = 0; i < centers.Count(); i++)
            {
                System.Diagnostics.Debug.WriteLine("Testing "+ centers.ElementAt(i).activity);
                double [] center = centers.ElementAt(i).center;

                System.Diagnostics.Debug.WriteLine("" + values.Length + "      " + center.Length);

                int count = center.Length > values.Length ? values.Length : center.Length;
                double distance = 0;

                for(int k = 0; k < count; k++)
                {
                    System.Diagnostics.Debug.WriteLine("values : " + values[k] + "  center : " + center[k]);
                    distance += (values[k]-center[k])* (values[k] - center[k]);
                }

                if(best < 0 || distance < bestDistance)
                {
                    best = i;
                    bestDistance = distance;
                    bestActivity = centers.ElementAt(i).activity;
                }
                System.Diagnostics.Debug.WriteLine("Distance is " + distance.ToString("0.000"));
                System.Diagnostics.Debug.WriteLine("Best Distance is " + bestDistance.ToString("0.000"));
            }

            return bestActivity;
        }
    }
}
