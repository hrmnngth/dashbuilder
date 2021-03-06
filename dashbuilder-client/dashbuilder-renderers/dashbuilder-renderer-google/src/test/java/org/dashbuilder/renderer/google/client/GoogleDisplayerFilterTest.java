/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.renderer.google.client;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.sort.SortOrder;
import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.DisplayerSettingsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.dashbuilder.dataset.ExpenseReportsData.*;
import static org.dashbuilder.dataset.group.AggregateFunctionType.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoogleDisplayerFilterTest extends GoogleDisplayerTest {

    DisplayerSettings byYear = DisplayerSettingsFactory.newBarChartSettings()
            .dataset(EXPENSES)
            .group(COLUMN_DATE)
            .column(COLUMN_DATE)
            .column(COLUMN_AMOUNT, SUM)
            .filterOn(false, true, true)
            .sort(COLUMN_DATE, SortOrder.ASCENDING)
            .buildSettings();

    @Test
    public void testFilter() {

        // Google renderer draw calls are processed asynchronously
        // A ready() call needs to be executed in order to ignite the real chart display
        GoogleBarChartDisplayer barChart = createBarChartDisplayer(byYear);
        GoogleBarChartDisplayer.View barChartView = barChart.getView();
        barChart.ready();

        // Select first bar
        reset(barChartView);
        barChart.onCategorySelected(COLUMN_DATE, 0);

        verify(barChartView).clearFilterStatus();
        verify(barChartView).addFilterValue("2012");
        verify(barChartView).addFilterReset();
        verify(barChartView).drawChart();

        // Select another bar
        reset(barChartView);
        barChart.onCategorySelected(COLUMN_DATE, 1);
        verify(barChartView).clearFilterStatus();
        verify(barChartView).addFilterValue("2012");
        verify(barChartView).addFilterValue("2013");
        verify(barChartView).addFilterReset();
        verify(barChartView).drawChart();

        // Reset the filter
        reset(barChartView);
        barChart.onFilterResetClicked();
        verify(barChartView).clearFilterStatus();
        verify(barChartView, never()).addFilterValue(anyString());
        verify(barChartView, never()).addFilterReset();
        verify(barChartView).drawChart();
    }
}