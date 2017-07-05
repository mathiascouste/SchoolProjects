using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage;
using System.IO;
using Parse;

namespace Accelerometer
{
    class DataEntry
    {
        public static List<DataEntry> dataEntries = new List<DataEntry>();
        public static List<DataEntry> filtredDatas = new List<DataEntry>();
        public static int FRAME_WIDTH = 20;

        private DateTime dateTime;
        public string activity;
        public Dictionary<string, Double> datas;
        public int partition = -1;
        private bool isUp = false;

        public DataEntry()
        {
            this.dateTime = DateTime.Now;
            this.activity = "N/C";
            this.datas = new Dictionary<string, double>();
        }
        private static bool called = false;

        public static void createParseclient()
        {
            if (!called)
            {
                ParseClient.Initialize("6SyfT7V0HYExCXVm9ezXHqNAmCCAS9LIHGkNaOLW", "3aiXoiIkk9Ph6YfFocJr28ZATrSxqxNOnbgIp3GD");
                called = true;
            }
        }

        public static int addEntryToList(DataEntry dataEntry)
        {
            dataEntries.Add(dataEntry);
            return dataEntries.Count();
        }

        private string getDateTimeInStringFormat()
        {
            string str = "";
            str += dateTime.Year.ToString("0000");
            str += "/";
            str += dateTime.Month.ToString("00");
            str += "/";
            str += dateTime.Day.ToString("00");
            str += "/";
            str += dateTime.Hour.ToString("00");
            str += "/";
            str += dateTime.Minute.ToString("00");
            str += "/";
            str += dateTime.Second.ToString("00");
            return str;
        }

        public async static void saveAll()
        {
            createParseclient();
            for (int i = 0; i < dataEntries.Count(); i++)
            {
                DataEntry dE = dataEntries.ElementAt(i);
                if (dE.isUp == false)
                {
                    ParseObject testObject = new ParseObject("TestObject");
                    testObject["dateTime"] = dE.getDateTimeInStringFormat();
                    testObject["activity"] = dE.activity;
                    testObject["dictionary"] = dE.dictionaryString();
                    testObject["user"] = Login.userName;
                    await testObject.SaveAsync();
                }
            }
        }

        private string dictionaryString()
        {
            string str = "";
            IEnumerable<KeyValuePair<String, Double>> entries = datas.AsEnumerable();
            for(int i = 0; i < entries.Count(); i++)
            {
                str += entries.ElementAt(i).Key + "=" + entries.ElementAt(i).Value.ToString("0.00");
                if(i != entries.Count()-1)
                {
                    str += "|";
                }
            }
            return str;
        }

        private void setDictionaryEntriesFromString(string dictionary)
        {
            string[] entries = dictionary.Split('|');
            for(int i = 0; i < entries.Count(); i++)
            {
                string[] keyValue = entries.ElementAt(i).Split('=');
                datas.Add(keyValue[0], Double.Parse(keyValue[1]));
            }
        }

        public async static void loadAll()
        {
            dataEntries.Clear();

            createParseclient();

            var query = ParseObject.GetQuery("TestObject").WhereNotEqualTo("objectId", "toto").WhereEqualTo("user", Login.userName);
            IEnumerable<ParseObject> results = await query.FindAsync();

            if(results == null)
            {
                return;
            }
            for(int i = 0; i < results.Count(); i++)
            {
                ParseObject po = results.ElementAt(i);
                DataEntry dE = new DataEntry();
                string[] words = ((string)po["dateTime"]).Split('/');
                dE.dateTime = new DateTime(int.Parse(words[0]), int.Parse(words[1]), int.Parse(words[2]), int.Parse(words[3]), int.Parse(words[4]), int.Parse(words[5]));
                dE.activity = (string)po["activity"];
                string dico = (string)po["dictionary"];

                dE.loadDictionnary(dico);

                dE.isUp = true;

                dataEntries.Add(dE);
            }

        }

        private void loadDictionnary(string dico)
        {
            string [] elements = dico.Split('|');
            for(int i = 0; i  <elements.Count(); i++)
            {
                string []kd = elements.ElementAt(i).Split('=');
                string key = kd.ElementAt(0);
                double value;
                Double.TryParse(kd.ElementAt(1), out value);
                this.datas.Add(key, value);
            }
        }

        public static List<DataEntry> filterAndPreprossDatas(List<DataEntry> des, List<String> filter)
        {
            List<DataEntry> tmp = new List<DataEntry>();
            for (int i = 0; i < dataEntries.Count(); i++)
            {
                DataEntry dE = dataEntries.ElementAt<DataEntry>(i).filterOne(filter);
                tmp.Add(dE);
            }
            return doMSE(tmp);
        }

        public static List<DataEntry> doMSE(List<DataEntry> tmp)
        {
            int frameWidth = FRAME_WIDTH;
            int demiFrameWidth1 = frameWidth / 2;
            int demiFrameWidth2 = frameWidth - demiFrameWidth1;
            List<DataEntry> result = new List<DataEntry>();
            for(int i = demiFrameWidth1; i < tmp.Count() - demiFrameWidth2; i++)
            {
                DataEntry dE = new DataEntry();
                dE.activity = tmp[i].activity;
                dE.dateTime = tmp[i].dateTime;
                dE.isUp = tmp[i].isUp;
                dE.datas = new Dictionary<string, double>();
                for (int k = 0; k < tmp[i].datas.Count(); k++) {
                    string key = tmp[i].datas.ElementAt(k).Key;
                    double newValue = 0;
                    for (int j = -demiFrameWidth1; j < (demiFrameWidth2) -1; j++)
                    {
                        newValue += Math.Pow(tmp[i+j+1].datas[key] - tmp[i + j].datas[key], 2)/(frameWidth-1);
                    }
                    dE.datas.Add(key, newValue);
                }
                result.Add(dE);
            }
            return result;
        }

        public DataEntry filterOne(List<String> filter)
        {
            DataEntry dE = new DataEntry();
            dE.dateTime = this.dateTime;
            dE.activity = this.activity;
            for (int i = 0; i < filter.Count(); i++)
            {
                String key = filter.ElementAt<string>(i);
                dE.datas.Add(key, this.datas[key]);
            }
            return dE;
        }

        public static int getFiltedDataRows()
        {
            return filtredDatas.Count();
        }

        public static int getFiltedDataCols()
        {
            if (filtredDatas.Count() > 0)
            {
                DataEntry dE = filtredDatas.ElementAt<DataEntry>(0);
                return dE.datas.Count();
            }
            else
            {
                return 0;
            }
        }

        public double[] getRawDatas()
        {
            double[] rawDatas = new double[datas.Count()];
            for (int i = 0; i < datas.Count(); i++)
            {
                rawDatas[i] = datas.ElementAt(i).Value;
            }
            return rawDatas;
        }
    }
}
