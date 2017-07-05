using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Linq;
using System.Data.Linq.Mapping;
using System.ComponentModel;
using System.Linq.Expressions;
using Accelerometer;




namespace Accelerometer.DatabaseFiles
{
    class DataBaseFetch
    {
        public IList<Data_Entries> GetAllDataEntries()
        {
            IList<Data_Entries> list = null;
            using (DataBaseContext context = new DataBaseContext(DataBaseContext.DBConnectionString))
            {
                //j ai essaye de resoudre ce probleme mais jusqua maintenant pas de solution, je n avais pas ce probleme a mon prototype essayee
                IQueryable<Data_Entries> query = from c in context.dataEntries select c;
                list = query.ToList();
            }
            return list;
        }

        public List<DataEntry> getListAllDataEntries()
        {
            IList<Data_Entries> dtes = this.GetAllDataEntries();
            List<DataEntry> AllDataEntries = new List<DataEntry>();
            foreach (Data_Entries m in dtes)
            {
                DataEntry n = new DataEntry();
                n.activity = m.activity;
                n.datas = m.datas;

                AllDataEntries.Add(n);
            }
            return AllDataEntries;
        }
    }
}
