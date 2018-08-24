/*
  Copyright 2015 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.jugoterapia.josdem.loader;

class Response<D> {

  private Exception mException;

  private D mResult;

  static <D> Response<D> ok(D data){

    Response<D> response = new Response<D>();
    response.mResult = data;

    return  response;
  }

  static <D> Response<D> error(Exception ex){

    Response<D> response = new Response<D>();
    response.mException = ex;

    return  response;
  }

  public boolean hasError() {

    return mException != null;
  }

  public Exception getException() {

    return mException;
  }

  public D getResult() {

    return mResult;
  }
}
