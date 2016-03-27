package com.jugoterapia.josdem.model

import groovy.transform.CompileStatic

@CompileStatic
class Beverage {
  Integer id
  String name
  String ingredients
  String recipe
  Long categoryId

  @Override
  String toString() {
    name
  }
}
