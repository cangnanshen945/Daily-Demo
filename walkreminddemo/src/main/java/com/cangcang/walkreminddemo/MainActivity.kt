package com.cangcang.walkreminddemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.*
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var mSensorManager: SensorManager? = null

    //传感器
    private var sensorManager: SensorManager? = null
    //计步传感器类型 0-counter 1-detector
    private var stepSensor = -1
    private var isRecord = false
    private var hasRecord = false
    private var initStep = 0
    private var initTime = 0L
    private var lastTime = 0L
    private var lastStep = 0
    private var totalCount = 0
    private var watchingCount = 0
    private var disWatchingCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getStepDetector() {
        if (sensorManager != null) {
            sensorManager = null
        }
        // 获取传感器管理器的实例
        sensorManager = this
            .getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //android4.4以后可以使用计步传感器
        if (Build.VERSION.SDK_INT >= 19) {
            addCountStepListener()
        }
    }

    /**
     * 添加传感器监听
     */
    private fun addCountStepListener() {
        val countSensor = sensorManager!!.getDefaultSensor(TYPE_STEP_COUNTER)
        val detectorSensor = sensorManager!!.getDefaultSensor(TYPE_SIGNIFICANT_MOTION)
        sensorManager!!.registerListener(
            this@MainActivity,
            countSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager!!.registerListener(
            this@MainActivity,
            detectorSensor,
            // 200ms
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    /**
     * 由传感器记录当前用户运动步数，注意：该传感器只在4.4及以后才有，并且该传感器记录的数据是从设备开机以后不断累加，
     * 只有当用户关机以后，该数据才会清空，所以需要做数据保护
     */
    override fun onSensorChanged(event: SensorEvent) {
        when(event.sensor.type) {
            TYPE_STEP_COUNTER -> {
                val tempStep = event.values[0].toInt()
                if (hasRecord) {
                    if (isRecord) {
                        checkUserMotion(tempStep)
                    }
                } else {
                    hasRecord = true
                    lastStep = tempStep
                    lastTime = System.currentTimeMillis()
                }
            }
            TYPE_STEP_DETECTOR -> {
                if (event.values[0].toDouble() == 1.0) {
                    isRecord = true
                }
            }
            TYPE_ACCELEROMETER -> {
                calculateDegree()
            }
            TYPE_MAGNETIC_FIELD -> {
                calculateDegree()
            }
        }
    }

    private fun calculateDegree() {
        if (mGeomagnetic != null && mGravity != null) {
            if (SensorManager.getRotationMatrix(mMatinX, null, mGravity, mGeomagnetic)) {
                SensorManager.getOrientation(mMatinX, mValues)
                val yDegree = Math.toDegrees(mValues[2].toDouble()) + 180
                val xDegree = Math.toDegrees(mValues[1].toDouble()) + 180
                // 当手机横屏或竖屏处于水平状态，表示用户低头玩手机
                if ((yDegree > 150 && yDegree < 210) || (xDegree > 150 && xDegree < 210)) {
                    watchingCount++
                    disWatchingCount = 0
                } else {
                    disWatchingCount++
                }
                if (disWatchingCount > 5) {
                    watchingCount = 0
                    totalCount = 0
                    isRecord = false
                    unRegisterSensorEventListener()
                } else if (watchingCount > 10) {
                    DialogUtils.showRemindDialog(this)
                }
            }
        }
    }

    private fun checkUserMotion(tempStep: Int) {
        val step = tempStep - lastStep
        val time = System.currentTimeMillis()
        val speed = step / (time / 1000f)
        if (speed > 1) {
            totalCount++
            if (totalCount > 10) {
                checkPhoneDirection()
            }
        } else {
            totalCount = 0
            unRegisterSensorEventListener()
        }
    }


    private val mMatinX = FloatArray(9)

    private val mValues = FloatArray(3)

    private val mGravity: FloatArray? = null

    private val mGeomagnetic: FloatArray? = null

    private fun checkPhoneDirection() {
        registerSensorEventListener(this)
    }

    fun registerSensorEventListener(mContext: Context) {
        if (mSensorManager == null) {
            mSensorManager = mContext.getSystemService(SENSOR_SERVICE) as SensorManager
        }
        mSensorManager?.registerListener(
            this, mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        val acceleSensor: Sensor? = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager?.registerListener(
            this,
            acceleSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }


    fun unRegisterSensorEventListener() {
        mSensorManager?.unregisterListener(
            this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        )
        mSensorManager?.unregisterListener(
            this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        )
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}