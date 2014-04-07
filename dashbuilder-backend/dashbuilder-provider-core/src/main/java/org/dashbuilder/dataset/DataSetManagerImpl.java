/**
 * Copyright (C) 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.dataset;

import java.util.HashMap;
import java.util.Map;

import org.dashbuilder.model.dataset.DataSet;
import org.dashbuilder.model.dataset.DataSetManager;
import org.dashbuilder.model.dataset.DataSetOperation;
import org.dashbuilder.model.dataset.impl.DataSetImpl;

public class DataSetManagerImpl implements DataSetManager {

    protected Map<String,DataSet> dataSetCache = new HashMap<String, DataSet>();

    public DataSet createDataSet(String uuid) {
        DataSetImpl dataSet = new DataSetImpl();
        dataSet.setUUID(uuid);
        return dataSet;
    }

    public DataSet getDataSet(String uuid) throws Exception {
        DataSet dataSet = dataSetCache.get(uuid);
        if (dataSet == null) throw new Exception("DataSet not found for UUID: " + uuid);

        return dataSet;
    }

    public void registerDataSet(DataSet dataSet) {
        if (dataSet != null && dataSet.getUUID() != null) {
            dataSetCache.put(dataSet.getUUID(), dataSet);
        }
    }

    public DataSet refreshDataSet(String uuid) throws Exception {
        return null;
    }

    public DataSet transformDataSet(String uuid, DataSetOperation... ops) throws Exception {
        return null;
    }
}