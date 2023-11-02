package com.benmeddour.pokemongame

import android.location.Location


class Pokeman {
    var name:String ?= null
    var des:String ?= null
    var img:Int ?= null
    var Power:Double ?= null
    var location:Location ?= null
    var isCatch:Boolean = false
    constructor(name:String,des:String,img:Int,Power:Double,lat:Double,log :Double){
        this.name=name
        this.Power=Power
        this.des =des
        this.img=img
        location = Location(name)
        location!!.latitude=lat
        location!!.longitude=log

    }
}