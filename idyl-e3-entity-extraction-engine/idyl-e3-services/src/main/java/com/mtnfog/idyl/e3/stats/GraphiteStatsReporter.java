/*******************************************************************************
 * Copyright 2019 Mountain Fog, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.mtnfog.idyl.e3.stats;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import ai.idylnlp.model.stats.StatsReporter;

/**
 * Implementation of {@link StatsReporter} that reports
 * statistics to Graphite.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class GraphiteStatsReporter extends AbstractStatsReporter implements StatsReporter {
	
	/**
	 * Creates a new Graphite statistics reporter.
	 * @param graphiteHost The Graphite host.
	 * @param graphitePort The Graphite port.
	 * @param graphitePrefix The Graphite prefix.
	 * @param graphiteInterval The Graphite reporting interval in seconds.
	 */
	public GraphiteStatsReporter(String graphiteHost, int graphitePort, String graphitePrefix, long interval) {
		
		super();
		
		final Graphite graphite = new Graphite(new InetSocketAddress(graphiteHost, graphitePort));
		
		final GraphiteReporter graphiteReporter = GraphiteReporter.forRegistry(metrics)
		                                                  .prefixedWith(graphitePrefix)
		                                                  .convertRatesTo(TimeUnit.SECONDS)
		                                                  .convertDurationsTo(TimeUnit.MILLISECONDS)
		                                                  .filter(MetricFilter.ALL)
		                                                  .build(graphite);
		
		graphiteReporter.start(interval, TimeUnit.SECONDS);
		
	}

}
