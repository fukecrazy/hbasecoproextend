/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hbase.client.coprocessor.agg.impl;

import java.math.BigDecimal;
import java.util.List;
import org.apache.hadoop.hbase.client.coprocessor.MultiThreadScan.ScanAggRunable;
import org.apache.hadoop.hbase.client.coprocessor.agg.AggResultHandlerInterface;
import org.apache.hadoop.hbase.client.coprocessor.model.AggResult;

public class SumAggResultHandler implements AggResultHandlerInterface {

	private static AggResultHandlerInterface instance = new SumAggResultHandler();

	public static AggResultHandlerInterface getInstance() {
		return instance;
	}

	@Override
	public void aggResult(List<ScanAggRunable> lls,  AggResult result , String classType) throws IllegalStateException {
		BigDecimal lla = BigDecimal.ZERO;
		for (ScanAggRunable srn : lls) {
			AggResult agg = srn.getResult();
			if (agg == null)
				throw new IllegalStateException("sum can not be null");
			lla = lla.add(agg.getStgResult());
		}
		result.setStgResult(lla);
	}
}