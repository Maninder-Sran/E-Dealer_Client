package com.e_dealer.e_dealer_client.game

data class Game(val name: String = "", val desc: String = "", val maxPlayerCnt : Int = 0){

    companion object {

        fun serializeList(gameLs : List<Game>) : String {

            val builder = StringBuilder()
            builder.append("[")
            for (i in gameLs.indices) {
                if (i != gameLs.size-1) {
                    builder.append(gameLs[i].toString() + "~")
                }
                else{
                    builder.append(gameLs[i].toString())
                }
            }
            builder.append("]")
            return builder.toString()
        }

        fun deserializeList(jsonData : String) : List<Game> {
            val gameLs = ArrayList<Game>()

            val jsonStr = jsonData.replace("[","").replace("]","")

            val strLs = jsonStr.split("~")

            for(strObj in strLs) {
                val game = deserializeStringObj(strObj)
                gameLs.add(game)
            }

            return gameLs
        }

        private fun deserializeStringObj(strObj : String) : Game {
            val str = strObj.replace("{","").replace("}","")
            val fieldLs = str.split("|")

            return Game(fieldLs[0], fieldLs[1], fieldLs[2].toInt())
        }
    }

    override fun toString() : String {
        return "{$name|$desc|$maxPlayerCnt}"
    }
}
