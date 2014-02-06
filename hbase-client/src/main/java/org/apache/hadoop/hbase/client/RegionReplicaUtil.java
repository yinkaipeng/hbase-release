/**
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

package org.apache.hadoop.hbase.client;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.hbase.HRegionInfo;

/**
 * Utility methods which contain the logic for regions and replicas.
 */
@InterfaceAudience.Private
public class RegionReplicaUtil {

  /**
   * The default replicaId for the region
   */
  private static final int DEFAULT_REPLICA_ID = 0;

  /**
   * Returns the HRegionInfo for the given replicaId. HRegionInfo's correspond to
   * a range of a table, but more than one "instance" of the same range can be
   * deployed which are differentiated by the replicaId.
   * @param replicaId the replicaId to use
   * @return an HRegionInfo object corresponding to the same range (table, start and
   * end key), but for the given replicaId.
   */
  public static HRegionInfo getRegionInfoForReplica(HRegionInfo regionInfo, int replicaId) {
    if (regionInfo.getReplicaId() == replicaId) {
      return regionInfo;
    }
    HRegionInfo replicaInfo = new HRegionInfo(regionInfo.getTable(), regionInfo.getStartKey(),
      regionInfo.getEndKey(), regionInfo.isSplit(), regionInfo.getRegionId(), replicaId);

    replicaInfo.setOffline(regionInfo.isOffline());
    return replicaInfo;
  }

  /**
   * Returns the HRegionInfo for the default replicaId (0). HRegionInfo's correspond to
   * a range of a table, but more than one "instance" of the same range can be
   * deployed which are differentiated by the replicaId.
   * @return an HRegionInfo object corresponding to the same range (table, start and
   * end key), but for the default replicaId.
   */
  public static HRegionInfo getRegionInfoForDefaultReplica(HRegionInfo regionInfo) {
    return getRegionInfoForReplica(regionInfo, DEFAULT_REPLICA_ID);
  }

}
