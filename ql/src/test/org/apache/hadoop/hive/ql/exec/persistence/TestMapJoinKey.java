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
package org.apache.hadoop.hive.ql.exec.persistence;

import junit.framework.Assert;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class TestMapJoinKey {

  @Test
  public void testEqualityHashCode() throws Exception {
    MapJoinKey key1 = new MapJoinKey(new String[] {"key"});
    MapJoinKey key2 = new MapJoinKey(new String[] {"key"});
    Utilities.testEquality(key1, key2);
    key1 = new MapJoinKey(new Object[] {148, null});
    key2 = new MapJoinKey(new Object[] {148, null});
    Utilities.testEquality(key1, key2);
    key1 = new MapJoinKey(new Object[] {null, "key1"});
    key2 = new MapJoinKey(new Object[] {null, "key2"});
    Assert.assertFalse(key1.equals(key2));
  }
  @Test
  public void testHasAnyNulls() throws Exception {
    MapJoinKey key = new MapJoinKey(new String[] {"key", null});
    Assert.assertTrue(key.hasAnyNulls(null));
    // field 1 is not null safe
    Assert.assertTrue(key.hasAnyNulls(new boolean[] { false, false }));
    // field 1 is null safe
    Assert.assertFalse(key.hasAnyNulls(new boolean[] { false, true }));
    Assert.assertFalse(key.hasAnyNulls(new boolean[] { true, true }));
  }
  @Test
  public void testSerialization() throws Exception {
    MapJoinKey key1 = new MapJoinKey(new Object[] {new Text("field0"), null, new Text("field2")});
    MapJoinKey key2 = Utilities.serde(key1, "f0,f1,f2", "string,string,string");
    Utilities.testEquality(key1, key2);
  }
}
