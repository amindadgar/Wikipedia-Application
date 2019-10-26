package Models

object Urls {
    val BaseUrl:String="https://en.wikipedia.org/w/api.php"

    fun getSearch(term:String,skip:Int,take:Int):String{
        return BaseUrl+"?action=query"+
                "&formatversion=2"+
                "&generator=prefixsearch"+
                "&gpssearch=$term"+
                "&gpslimit=$take"+
                "&gpsoffset=$skip"+
                "&prop=pageimages|info|description"+
                "&piprop=thumbnail"+
                "&pithumbsize=200"+
                "&pilimit=$take"+
                "&format=json"+
                "&inprop=url"

    }
    fun getSearch2(term: String,take: Int):String{
        return "$BaseUrl?action=query" +
                "&formatversion=2" +
                "&list=search" +
                "&srsearch=$term" +
                "&srwhat=text"+
                "&srlimit=$take"+
                "&prop=pageimages|info|description"+
                "&piprop=thumbnail"+
                "&pithumbsize=200"+
                "&format=json"+
                "&srprop=redirecttitle"
    }
    fun getRandom(take:Int):String{
        return BaseUrl +"?action=query"+
                "&format=json"+
                "&formatversion=2"+
                "&generator=random"+
                "&grnnamespace=0"+
                "&prop=pageimages|info"+
                "&grnlimit=$take"+
                "&inprop=url"+
                "&pithumbsize=1500"

    }

}