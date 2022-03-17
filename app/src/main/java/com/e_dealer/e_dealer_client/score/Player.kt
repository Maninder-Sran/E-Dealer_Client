package com.e_dealer.e_dealer_client.score

import kotlinx.serialization.Serializable

@Serializable
data class Player(val name : String = "", var score : Double? = 0.0) {

    companion object {

        fun serializeList(players : List<Player>) : String {

            val builder = StringBuilder()
            builder.append("[")
            for (i in players.indices) {
                if (i != players.size-1) {
                    builder.append(players[i].toString() + ";")
                }
                else{
                    builder.append(players[i].toString())
                }
            }
            builder.append("]")
            return builder.toString()
        }

        fun deserializeList(jsonData : String) : List<Player> {
            val playersLs = ArrayList<Player>()

            val jsonStr = jsonData.replace("[","").replace("]","")

            val strLs = jsonStr.split(";")

            for(strObj in strLs) {
                val player = deserializeStringObj(strObj)
                if (player != null) {
                    playersLs.add(player)
                }
            }

            return playersLs
        }

        private fun deserializeStringObj(strObj : String) : Player? {
            val str = strObj.replace("{","").replace("}","")
            val fieldLs = str.split(",")

            if(fieldLs[0] == "NULL" || fieldLs.size == 1){
                return null
            }
            val size = fieldLs.size

            return Player(fieldLs[0], fieldLs[size-1].toDouble())
        }
    }

    override fun toString() : String {
        return "{" + name + "," + score.toString() + "}"
    }
}
