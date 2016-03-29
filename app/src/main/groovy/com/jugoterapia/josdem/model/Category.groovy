package com.jugoterapia.josdem.model

import groovy.transform.CompileStatic

@CompileStatic
class Category {
  Integer id
  String name

  @Override
  String toString() {
    name
  }
}
