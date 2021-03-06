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

/**
 * 
 */
package org.apache.hadoop.hive.metastore.model;

/**
 * Storage Class representing the Hive MDatabase in a rdbms
 *
 */
public class MDatabase {
  private String name;
  private String locationUri;
  private String description;

  /**
   * Default construction to keep jpox/jdo happy
   */
  public MDatabase() {}
  
  /**
   * To create a database object
   * @param name of the database
   * @param locationUri Location of the database in the warehouse
   * @param description Comment describing the database
   */
  public MDatabase(String name, String locationUri, String description) {
    this.name = name;
    this.locationUri = locationUri;
    this.description = description;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the location_uri
   */
  public String getLocationUri() {
    return locationUri;
  }

  /**
   * @param locationUri the locationUri to set
   */
  public void setLocationUri(String locationUri) {
    this.locationUri = locationUri;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

}
