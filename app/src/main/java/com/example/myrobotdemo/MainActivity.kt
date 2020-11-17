  package com.example.myrobotdemo
  
  import android.os.Bundle
  import android.util.Log
  import android.view.View
  import android.view.inputmethod.InputMethodManager
  import android.widget.Button
  import android.widget.EditText
  import android.widget.TextView
  import androidx.appcompat.app.AppCompatActivity
  import androidx.core.widget.doAfterTextChanged

  class MainActivity : AppCompatActivity() {
    private lateinit var etXRoomValue: EditText
    private lateinit var etYRoomValue: EditText
    private lateinit var etXPosition: EditText
    private lateinit var etYPosition: EditText
    private lateinit var etDirection: EditText
    private lateinit var etCommand: EditText
    private lateinit var tvResult : TextView
    private lateinit var btnResult: Button
    private lateinit var robot: Robot
    
    private var xValue = 0
    private var yValue = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      etXRoomValue = findViewById(R.id.et_min_size)
      etYRoomValue = findViewById(R.id.et_max_size)
      etXPosition = findViewById(R.id.et_x_position)
      etYPosition = findViewById(R.id.et_y_position)
      etDirection = findViewById(R.id.et_direction)
      etCommand = findViewById(R.id.et_commands)
      tvResult = findViewById(R.id.tv_result)
      btnResult  = findViewById(R.id.btn_result)
      
      etXRoomValue.doAfterTextChanged {
        xValue =  if(it.isNullOrEmpty()) {0} else {it.toString().toInt().minus(1)}
      }
      etYRoomValue.doAfterTextChanged {
          yValue =  if(it.isNullOrEmpty()) {0} else {it.toString().toInt().minus(1)}
      }
    }
    
    fun executeCommand(view: View) {
       hideKeyboard(view)
  
      // check for room size greater than 0
      if(xValue < 1 && yValue < 1) {
        tvResult.text = "Room size is required"
        return
      }
     
       val xPosition =  if(etXPosition.text.isNullOrEmpty()){
         tvResult.text = "Robot X position required"
         return
       } else{ etXPosition.text.toString().toInt()}
       val yPosition = if(etYPosition.text.isNullOrEmpty()) {
         tvResult.text = "Robot Y position required"
         return
       } else { etYPosition.text.toString().toInt()}
       val directionValue = if(etDirection.text.isNullOrEmpty()) {
         tvResult.text = "Robot direction face required"
         return
       } else {etDirection.text.toString()}
       val commands = etCommand.text.map{ it.toString() }.toTypedArray()
  
      // check robot position not greater than room size
      if(xPosition > xValue || yPosition > yValue){
        tvResult.text = "Robot Position can not be equal or greater than room size"
        return
      }
       
       robot =  Robot(xPosition, yPosition, Direction.valueOf(directionValue))
      
       if(commands.isNotEmpty()) {
         for (d in commands) {
           when (d) {
             "F" -> moveForward()
             "R" -> turnRight()
             "L" -> turnLeft()
           }
         }
         tvResult.text = robot.printReport()
       }else{
         tvResult.text = "There is no command"
       }
    }
    
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun moveForward(){
        
        when (robot.direction) {
          Direction.S -> if (robot.yPosition!! < (yValue)) {
            robot.increaseYPosition()
            Log.d("ROBOT S MOVING", robot.printReport())
          }
          else {
            Log.d("ROBOT S BLOCKED", robot.printReport())
          }
          Direction.N -> if (robot.yPosition!! > 0) {
            robot.decreaseYPosition()
            Log.d("ROBOT N MOVING", robot.printReport())
          }
          else {
            Log.d("ROBOT N BLOCKED", robot.printReport())
          }
          Direction.E -> if (robot.xPosition!! < (xValue)) {
            robot.increaseXPosition()
            Log.d("ROBOT E MOVING", robot.printReport())
          }
          else {
            Log.d("ROBOT E BLOCKED", robot.printReport())
          }
          Direction.W -> if (robot.xPosition!! > 0) {
            robot.decreaseXPosition()
            Log.d("ROBOT W MOVING", robot.printReport())
          }
          else {
            Log.d("ROBOT W BLOCKED", robot.printReport())
          }
        }
      }
    
    private fun turnLeft(){
      robot.direction = when (robot.direction) {
        Direction.N -> Direction.W
        Direction.W -> Direction.S
        Direction.S -> Direction.E
        Direction.E -> Direction.N
      }
      Log.d("ROBOT TURNING LEFT", robot.printReport().toString())
     
    }
    
    private fun turnRight(){
      robot.direction = when (robot.direction) {
        Direction.N -> Direction.E
        Direction.E -> Direction.S
        Direction.S -> Direction.W
        Direction.W -> Direction.N
      }
      Log.d("ROBOT TURNING RIGHT", robot.printReport().toString())
    }
    
    private fun hideKeyboard(v: View) {
      val inputMethodManager: InputMethodManager =
        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
      inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }
  }