import java.util.*
import kotlin.collections.ArrayList

/* 동물원

동물사육장, 해양생물놀이터, 방목장, 밀림

동물 사육장은 모든 동물들이 있는 곳
해양생물놀이터는 돌고래와 상어가 있는 곳
방목장은 말과 기린이 있는 곳
밀림은 호랑이와 사자가 있는 곳

동물원에 있는 모든 동물들은 동물 사육장에 넣어주면 모든 동물들이 밥을 먹는다.
동물원에 있는 모든 동물들을 해양생물 놀이터에 넣어주면 돌고래와 상어가 헤엄을 친다.
동물원에 있는 모든 동물들을 방목장에 넣어주면 말과 기린과 호랑이만 뛰어 다닌다.
동물원에 있는 모든 몽물들을 밀림에 넣어주면 상어와 기린과 사자가 사냥을 한다.

돌고래는 밥을 먹을 때 "냠냠냠"하고 먹는다.
상어는 밥을 먹을 때 "얌얌얌"하고 먹는다.
말은 밥을 먹을 때 "당근 당근"하고 먹는다.
기린은 밥을 먹을 때 "풀풀풀" 하고 먹는다.
호랑이가 밥을 먹을 때 "아작 아작"하고 먹는다.
사자가 법을 먹을 때 "꿀꺽 꿀꺽" 하고 는다.

돌고래는 헤엄을 칠 때 "돌~돌~"하면서 헤엄을 친다.
상어가 헤엄을 칠 때 "슈웅 슈융" 하면서 헤엄을 친다.
말이 뛰어 다닐 때닌 "이히히히힝~" 하면서 뛰어 다니고
기린이 뛰어 다닐 때는 "영차~ 영차~" 하면서 뛰어 다닌다.
호랑이가 뛰어 다닐 때는 "헥~ 헥~" 하면서 뛰어 다닌다.
상어가 사냥을 하면 "으아아아아아!!!" 하면서 사냥을 한다.
호랑이가 사냥을 하면 "가즈아~" 하고 사냥을 한다.
사자가 사냥을 하면 "암컷아 사냥해와라~"하고 사냥을 한다.

프로그램이 시작되면 다음과 같이 입력을 받는다.
어떤 동물을 동물원에 넣어줄까요?
1. 돌고래, 2. 상어, 3. 말, 4. 기린, 5. 호랑이, 6. 사자, 0. 그만넣어 :

번호를 잘못 입력하면 잘못 입력하였습니다를 보여준다.
0을 입력하면 입력을 중단한다.

모든 동물들을 사육장에 넣어 결과를 출력한다.
모든 동물들을 해양생물 놀이터에 넣어 결과를 출력한다.
모든 동물들을 방목장에 넣어 결과를 출력한다.
모든 동물들을 밀림에 넣어 결과를 출력한다.

밥먹는 것, 헤엄치는 것, 뛰어 다는 것, 사냥하는 것은 모두 메서드로 만들어준다.

--- 출력 ---

타입 : 돌고래
크기 : 돌고래 만큼 크다
헤엄 속도 : 300노트

타입 : 상어
크기 : 상어 만큼 크다
헤엄 속도 : 500노트

타입 : 말
크기 : 말 만큼 크다
달리기 속도 : 300km/h

타입 : 기린
크기 : 기린 만큼 크다
달리기 속도 : 500km/h

타입 : 호랑이
크기 : 호랑이 만큼 크다
먹이량 : 500마리

타입 : 사자
크기 : 사자만큼 크다
먹이량 : 600마리

돌고래는 밥을 먹을 때 "냠냠냠"하고 먹는다.
상어는 밥을 먹을 때 "얌얌얌"하고 먹는다.
말은 밥을 먹을 때 "당근 당근"하고 먹는다.
기린은 밥을 먹을 때 "풀풀풀" 하고 먹는다.
호랑이가 밥을 먹을 때 "아작 아작"하고 먹는다.
사자가 법을 먹을 때 "꿀꺽 꿀꺽" 하고 는다.

돌고래는 헤엄을 칠 때 "돌~돌~"하면서 헤엄을 친다.
상어가 헤엄을 칠 때 "슈웅 슈융" 하면서 헤엄을 친다.
말이 뛰어 다닐 때닌 "이히히히힝~" 하면서 뛰어 다니고
기린이 뛰어 다닐 때는 "영차~ 영차~" 하면서 뛰어 다닌다.
호랑이가 뛰어 다닐 때는 "헥~ 헥~" 하면서 뛰어 다닌다.
상어가 사냥을 하면 "으아아아아아!!!" 하면서 사냥을 한다.
호랑이가 사냥을 하면 "가즈아~" 하고 사냥을 한다.
사자가 사냥을 하면 "암컷아 사냥해와라~"하고 사냥을 한다.

모든 출력은 항상 입력한 순서대로 출력한다. */

fun main() {
    var zoo = Zoo()
    zoo.selectAnimal()
    zoo.printAnimalInfo()
    zoo.printEating()
    zoo.printEtc()
}

class Zoo{
    val scan = Scanner(System.`in`)
    val animalList = ArrayList<Animal>()

    fun selectAnimal(){
        var selectNumber = 0

        while (true){
            println("어떤 동물을 동물원에 넣어줄까요?")
            print("1. 돌고래, 2. 상어, 3. 말, 4. 기린, 5. 호랑이, 6. 사자, 0. 그만넣어 : ")
            selectNumber = scan.nextInt()

            if(selectNumber !in 0..6){
                println("번호를 다시 입력해주세요")
                continue
            }
            if(selectNumber == 0){
                break
            }

            when(selectNumber){
                1 ->{
                    val animal = Dolphin("돌고래","돌고래 만큼 크다",300)
                    animalList.add(animal)
                }
                2 ->{
                    val animal = Shark("상어","상어 만큼 크다",500)
                    animalList.add(animal)
                }
                3 ->{
                    val animal = Horse("말","말 만큼 크다",300)
                    animalList.add(animal)
                }
                4 ->{
                    val animal = Giraffe("기린","기린 만큼 크다",500)
                    animalList.add(animal)
                }
                5 ->{
                    val animal = Tiger("호랑이","호랑이 만큼 크다",500)
                    animalList.add(animal)
                }
                6 ->{
                    val animal = Lion("사자","사자 만큼 크다",600)
                    animalList.add(animal)
                }
            }
        }
    }

    fun printAnimalInfo(){
        for (animal in animalList){
            animal.printAnimalInfo()
        }
        println("---------------------------")
    }

    fun printEating(){
        for (animal in animalList){
            animal.eating()
        }
        println("---------------------------")
    }

    fun printEtc(){
        for (animal in animalList){
            when(animal.type){
                "돌고래", "상어" -> {
                    animal.swimming()
                }
                 "말", "기린"-> {
                    animal.running()
                }
                "사자", "호랑이" -> {
                    animal.hunting()
                }
            }
        }
        println("---------------------------")
    }
}

open abstract class Animal(var type:String, var size:String) {
    open abstract fun printAnimalInfo()
    open abstract fun eating()
    open fun swimming() {}
    open fun running() {}
    open fun hunting() {}
}

class Dolphin : Animal, Marine{
    var swimSpeed = 0

    constructor(type:String,size:String, swimSpeed:Int): super(type, size){
        this.swimSpeed = swimSpeed
    }

    override fun printAnimalInfo() {
        println("타입 : $type")
        println("크기 : $size")
        println("헤엄 속도 : ${swimSpeed}노트")
    }

    override fun eating() {
        println("돌고래는 밥을 먹을 때 \"냠냠냠\"하고 먹는다.")
    }

    open override fun swimming() {
        println("돌고래는 헤엄을 칠 때 \"돌~돌~\"하면서 헤엄을 친다.")
    }
}

class Shark : Animal, Marine{
    var swimSpeed = 0

    constructor(type:String,size:String, swimSpeed:Int): super(type, size){
        this.swimSpeed = swimSpeed
    }

    override fun eating() {
        println("상어는 밥을 먹을 때 \"얌얌얌\"하고 먹는다.")
    }

    override fun swimming() {
        println("상어가 헤엄을 칠 때 \"슈웅 슈융\" 하면서 헤엄을 친다.")
    }

    override fun printAnimalInfo() {
        println("타입 : $type")
        println("크기 : $size")
        println("헤엄 속도 : ${swimSpeed}노트")
    }
}

class Horse : Animal, Garden{
    var runSpeed = 0

    constructor(type:String,size:String, runSpeed:Int): super(type, size){
        this.runSpeed = runSpeed
    }

    override fun eating() {
        println("말은 밥을 먹을 때 \"당근 당근\"하고 먹는다.")
    }

    override fun running() {
        println("말이 뛰어 다닐 때는 \"이히히히힝~\" 하면서 뛰어 다닌다.")
    }

    override fun printAnimalInfo() {
        println("타입 : $type")
        println("크기 : $size")
        println("헤엄 속도 : ${runSpeed}km/h")
    }
}

class Giraffe : Animal, Garden{
    var runSpeed = 0

    constructor(type:String,size:String, runSpeed:Int): super(type, size){
        this.runSpeed = runSpeed
    }

    override fun eating() {
        println("기린은 밥을 먹을 때 \"풀풀풀\" 하고 먹는다.")
    }

    override fun running() {
        println("기린이 뛰어 다닐 때는 \"영차~ 영차~\" 하면서 뛰어 다닌다.")
    }

    override fun printAnimalInfo() {
        println("타입 : $type")
        println("크기 : $size")
        println("헤엄 속도 : ${runSpeed}km/h")
    }
}

class Tiger : Animal, Jungle{
    var food = 0
    constructor(type:String,size:String, food:Int): super(type, size){
        this.food = food
    }

    override fun eating() {
        println("호랑이가 밥을 먹을 때 \"아작 아작\"하고 먹는다.")
    }

    override fun hunting() {
        println("호랑이가 사냥을 하면 \"가즈아~\" 하고 사냥을 한다.")
    }

    override fun printAnimalInfo() {
        println("타입 : $type")
        println("크기 : $size")
        println("헤엄 속도 : ${food}마리")
    }
}

class Lion : Animal, Jungle{
    var food = 0
    constructor(type:String,size:String, food:Int): super(type, size){
        this.food = food
    }

    override fun eating() {
        println("사자가 밥을 먹을 때 \"꿀꺽 꿀꺽\" 하고 는다.")
    }

    override fun hunting() {
        println("사자가 사냥을 하면 \"암컷아 사냥해와라~\"하고 사냥을 한다.")
    }

    override fun printAnimalInfo() {
        println("타입 : $type")
        println("크기 : $size")
        println("헤엄 속도 : ${food}마리")
    }
}

interface Marine{
    fun swimming()
}

interface Garden{
    fun running()
}

interface Jungle{
    fun hunting()
}