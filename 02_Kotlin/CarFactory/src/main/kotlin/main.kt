import java.util.*
import kotlin.collections.ArrayList

/* 자동차공장

생산할 자동차를 선택해주세요
1. 붕붕, 2. 승용차, 3. 버스, 4. 트럭, 0. 생산종료

입력이 완료되면 다음과 같이 출력한다.
총 생산 자동차 수 : 000대
붕붕 : 00대
승용차 : 00대
버스 : 00대
트럭 : 00대

종류 : 붕붕
최대속도 : 300km/h
연료 : 꽃향기
탑승인원수 : 1명

종류 : 승용차
최대속도 : 200km/h
연로 : 휘발유
탑승인원수 : 4명

종류 : 버스
최대속도 : 150km/h
연료 : 가스
탑승인원수 : 50명

종류 : 트럭
최대속도 : 100km/h
연료 : 가스
탑승인원수 : 3명

생산된 자동차들의 평균 속도 : 000km/h
생산된 자동차들의 총 탑승인원수 : 0000명
연료가 꽃향기인 자동차의 수 : 00대
연료가 휘발유인 자동차의 수 : 00대
연료가 가스인 자동차의 수 : 00대 */

fun main() {
    val carFactory = CarFactory()

    // 생산할 장난감 번호를 입력 받는다.
    var selectNumber = 0

    // 0을 입력할 때까지 반복한다.
    do{
        selectNumber = carFactory.selectCarType()

        // 장난감을 생산한다.
        if(selectNumber != 0){
            val toy = carFactory.createCar(selectNumber)

            // 장난감을 저장한다.
            carFactory.addToy(toy)
        }

    }while(selectNumber != 0)

    carFactory.printToyCount()

    carFactory.printCarInfo()

    carFactory.printCarTotalInfo()
}

class CarFactory{
    val carList = ArrayList<Car>()
    val scan = Scanner(System.`in`)
    fun selectCarType() :Int{
        var selectNumber = 0

        do{
            println("생산할 자동차를 선택해주세요")
            print("1. 붕붕, 2. 승용차, 3. 버스, 4. 트럭, 0. 생산종료")
            print("번호 입력 : ")
            selectNumber = scan.nextInt()

            if (selectNumber !in 0..4){
                println("잘못 입력하였습니다.")
            }
        }while(selectNumber !in 0..4)
        return selectNumber
    }

    fun createCar(carType:Int) = when(carType){
        1 -> Car("붕붕", 300, "꽃향기", 4)
        2 -> Car("승용차", 200, "휘발유", 4)
        3 -> Car("버스", 150, "가스", 50)
        4 -> Car("트럭", 100, "가스", 3)
        else -> Car("자동차", 0, "연료", 0)
    }

    // 생산된 장난감을 저장한다.
    fun addToy(car:Car) {
        carList.add(car)
    }

    fun printToyCount() {

        var bungbungCount = 0
        var sedanCount = 0
        var busCount = 0
        var truckCount = 0

        println("총 : ${carList.size}대")

        // 각 장난감의 갯수를 구한다.
        for (tempCar in carList) {
            when (tempCar.type) {
                "붕붕" -> bungbungCount++
                "승용차" -> sedanCount++
                "버스" -> busCount++
                "트럭" -> truckCount++
            }
        }
        println("--------------------")
        println("붕붕 : $bungbungCount 대")
        println("승용차 : $sedanCount 대")
        println("버스 : $busCount 대")
        println("트럭 : $truckCount 대")
        println("--------------------")
    }

    fun printCarInfo(){
        // 장난감의 수 만큼 반복한다.
        for(tempCar in carList){
            tempCar.printCarInfo()
        }
    }

    fun printCarTotalInfo(){
        var speedTotal = 0
        var peopleTotal = 0
        var fuelFlower = 0
        var fuelGasoline = 0
        var fuelGas = 0

        for(tempCar in carList){
            speedTotal += tempCar.speed
            peopleTotal += tempCar.people
            when(tempCar.fuel){
                "꽃향기" -> fuelFlower++
                "휘발유" -> fuelGasoline++
                "가스" -> fuelGas++
            }
        }
        println("--------------------")
        println("생산된 자동차들의 평균 속도 : ${speedTotal/carList.size}km/h")
        println("생산된 자동차들의 총 탑승 인원 수 : ${peopleTotal}명")
        println("연료가 꽃향기인 자동차의 수 : ${fuelFlower}대")
        println("연료가 휘발유인 자동차의 수 : ${fuelGasoline}대")
        println("연료가 가스인 자동차의 수 : ${fuelGas}대")
        println("--------------------")
    }
}

class Car(var type:String, var speed:Int, var fuel:String, var people:Int){
    fun printCarInfo(){
        println("--------------------")
        println("종류 : $type")
        println("최대 속도 : ${speed}km/h")
        println("연료 : $fuel")
        println("탑승 인원 수 : ${people}명")
        println("--------------------")
    }
}

