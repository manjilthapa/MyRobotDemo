package com.example.myrobotdemo

class Robot(internal var xPosition: Int?, var yPosition: Int?, var direction: Direction) {
  
  fun printReport(): String? {
    return "The report result:  ${xPosition.toString()} ${yPosition.toString()} $direction"
  }
  
  fun increaseYPosition() {
   this.yPosition = yPosition?.plus(1)
  }
  
  fun decreaseYPosition() {
    this.yPosition = yPosition?.minus(1)
  }
  
  fun increaseXPosition() {
    this.xPosition = xPosition?.plus(1)
  }
  
  fun decreaseXPosition() {
    this.xPosition = xPosition?.minus(1)
  }
}