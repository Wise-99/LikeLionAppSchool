import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Scanner

class MemoManagementClass(var scanner: Scanner, var mainClass: MainClass){

    var choiceCategory = ""
    var choiceMemo = 0

    // 메모를 담을 리스트
    val memoList = mutableListOf<MainClass.MemoClass>()

    // 카테고리 선택 메서드
    fun choiceCategory(): Int {
        while (true){
            try{
                println()
                print("선택할 카테고리 번호를 입력해주세요(0. 이전) : ")
                val inputCategoryNumber = scanner.next().toInt()

                if (inputCategoryNumber !in 0..mainClass.categoryList.size){
                    println()
                    println("잘못 입력하였습니다.")
                } else{
                    if (inputCategoryNumber != 0){
                        choiceCategory = mainClass.categoryList[inputCategoryNumber-1]
                    }
                    return inputCategoryNumber
                }

            } catch(e:Exception){
                println()
                println("잘못 입력하였습니다.")
            }
        }
    }

    // 메모 메뉴 선택 메서드
    fun memoManagement(): Int {
        while (true){
            try{
                println()
                print("1.메모보기, 2.메모등록, 3.메모수정, 4.메모삭제, 5.이전 : ")
                val inputMemoNumber = scanner.next().toInt()
                if (inputMemoNumber !in 1..5){
                    println()
                    println("잘못 입력 하였습니다.")
                } else{
                    return inputMemoNumber
                }

            } catch(e:Exception){
                println()
                println("잘못 입력 하였습니다.")
            }
        }
    }

    // 각 카테고리의 메모들을 불러와 출력하는 메서드
    fun getMemoList(category: String){
        memoList.clear()
        val file = File("./${category}.cat")

        // 파일에 내용이 있다면 객체를 읽어온다
        if (file.length() != 0L){
            val fis = FileInputStream("${category}.cat")
            val ois = ObjectInputStream(fis)

            try{
                while (true){
                    val memo = ois.readObject() as MainClass.MemoClass
                    memoList.add(memo)
                }
            } catch (e:Exception){
                ois.close()
                fis.close()
            }
        }
    }

    fun printMemoList(category: String){
        getMemoList(category)

        println()
        if (memoList.size == 0){
            println("등록된 메모가 없습니다.")
        } else {
            println("---------- Memo List ----------")
            for (i in 0 until memoList.size){
                println("${i+1} : ${memoList[i].title}")
            }
        }
    }

    // 메모를 선택하는 메서드
    fun showMemo(){
        while (true){
            try{
                print("확인할 메모의 번호를 입력해주세요(0. 이전) : ")
                choiceMemo = scanner.next().toInt()

                if (choiceMemo in 0 .. memoList.size){
                    break
                } else {
                    println()
                    println("잘못 입력하였습니다.")
                }

            } catch (e:Exception){
                println()
                println("잘못 입력하였습니다.")
            }
        }
    }

    // 메모를 출력하는 메서드
    fun printMemo() {
        println()
        if (choiceMemo-1 in 0 until memoList.size){
            println("제목 : ${memoList[choiceMemo-1].title}")
            println("내용 : ${memoList[choiceMemo-1].content}")
        } else{
            println("해당 메모가 존재하지 않습니다.")
        }

        while (true){
            try {
                println()
                print("이전으로 돌아가려면 0을 입력하세요 : ")
                val num = scanner.next().toInt()
                if (num == 0){
                    break
                } else {
                    println("잘못 입력하였습니다.")
                    continue
                }
            } catch (e:Exception){
                println("잘못 입력하였습니다.")
            }
        }
    }

    // 메모를 추가하는 메서드
    fun insertMemo(){

        println()
        scanner.nextLine()
        print("메모 제목 : ")
        val title = scanner.nextLine()

        print("메모 내용 : ")
        val content = scanner.nextLine()

        val memo = MainClass.MemoClass(title, content)

        memoList.add(memo)
        setMemoList()
        println("메모 저장 완료!")
    }

    fun setMemoList(){
        val fos = FileOutputStream("${choiceCategory}.cat")
        val oos = ObjectOutputStream(fos)

        for (m in memoList){
            oos.writeObject(m)
        }

        oos.flush()
        oos.close()
        fos.close()
    }

    // 메모를 수정하는 메서드
    fun updateMemo(){
        while (true){
            try {
                print("수정할 메모의 번호를 입력해주세요(0. 이전) : ")
                val updateMemoNumber = scanner.next().toInt()

                // 0이 아닐 때만 동작
                if (updateMemoNumber != 0){
                    println()
                    println("제목 : ${memoList[updateMemoNumber - 1].title}")
                    print("메모의 새로운 제목을 입력해주세요.(0 입력시 무시합니다) : ")
                    scanner.nextLine()
                    val updateTitle = scanner.nextLine()

                    println("내용 : ${memoList[updateMemoNumber - 1].content}")
                    print("메모의 새로운 내용을 입력해주세요.(0 입력시 무시합니다) : ")
                    val updateContent = scanner.nextLine()

                    if (updateTitle != "0"){
                        memoList[updateMemoNumber - 1].title = updateTitle
                    }

                    if (updateContent != "0"){
                        memoList[updateMemoNumber - 1].content = updateContent
                    }

                    setMemoList()
                    println("메모 수정 완료!")
                }
                break

            } catch (e:Exception){
                println()
                println("잘못 입력하였습니다.")
            }
        }
    }

    // 메모를 삭제하는 메서드
    fun deleteMemo(){
        while (true){
            try {
                print("삭제할 메모의 번호를 입력해주세요(0. 이전) : ")
                val deleteMemoNumber = scanner.next().toInt()

                if (deleteMemoNumber != 0){
                    memoList.removeAt(deleteMemoNumber - 1)
                    setMemoList()

                    println()
                    println("메모 삭제 완료!")
                }
                break

            } catch (e:Exception){
                println()
                println("잘못 입력하였습니다.")
            }
        }


    }
}

enum class MemoMenuItem(val number:Int){
    // 1.메모보기
    SHOW_MEMO(1),
    // 2.메모등록
    INSERT_MEMO(2),
    // 3.메모수정
    UPDATE_MEMO(3),
    // 4.메모삭제
    DELETE_MEMO(4),
    // 5.이전
    BEFORE_MEMO(5)
}