/*1. 프로그램 실행시 설정된 비밀번호가 없다면 비밀번호를 설정하는 기능이 먼저 나와야 한다.
2. 설정된 비밀번호가 있다면 로그인 화면이 나온다.
3. 제목과 내용은 띄어쓰기 입력을 허용한다.
4. 모든 입력에 대해 잘못된 입력(허용하지 않는 숫자나 문자열 입력히)에 대해 잘못된 입력이라는 문구를 보여줘야 한다.
5. 카테고리나 메모가 등록된게 없을 경우 카테고리나 메모 목록을 보여주는 화면에서는 등록된 것이 없다는 메시지를 보여줘야 한다.
6. 비밀번호, 카테고리 정보, 메모 내용 정보 등은 모두 파일에 저장을 하고 파일에서 불러와 보여주는 것으로 처리 한다.*/

/*1. 예상 가능한 상태들을 enum class로 정의한다.
2. 정의한 상태별로 while 문 내부를 분기한다.
3. 각 상태에 대한 클래스들을 정의한다.
4. 각 상태에서의 입출력 부분을 모두 구현한다.
5. 구현된 화면을 보고 저장할 데이터들을 선별한다.
6. 데이터 저장 관련 기능을 구현하면서 프로그램과 연동시킨다.*/

import java.io.File
import java.io.Serializable
import java.util.Scanner
import kotlin.system.exitProcess

fun main(){
    val mainClass = MainClass()
    mainClass.running()
}

// 메인 클래스
class MainClass{

    val scanner = Scanner(System.`in`)

    // 각 상태별 객체를 생성한다.
    val mainMenuClass = MainMenuClass(scanner)
    val categoryManagementClass = CategoryManagementClass(scanner, this)
    val memoManagementClass = MemoManagementClass(scanner, this)
    val passwordClass = PasswordClass(scanner)

    // 프로그램 상태를 담는 변수에 초기 상태를 설정
    var programState = ProgramState.PROGRAM_STATE_INPUT_PASSWORD
    lateinit var categoryManagementItem : CategoryManagementItem
    // 메모 정보를 담을 클래스
    data class MemoClass(var title:String, var content:String) : Serializable

    // 파일 목록(카테고리)을 담을 리스트
    val categoryList = mutableListOf<String>()


    // 프로그램 상태 전체를 관리하며 운영하는 메서드
    fun running(){
        while(true) {
            // 프로그램 상태에 따른 분기
            when (programState) {
                // 메인 메뉴를 보여주는 상태
                ProgramState.PROGRAM_STATE_SHOW_MENU ->{
                    val inputMenuNumber = mainMenuClass.inputMainMenuNumber()

                    when(inputMenuNumber){
                        // 1. 메인 메뉴 관리
                        MainMenuItem.MAIN_MENU_MANAGEMENT_CARTEGORY.itemNumber -> {
                            programState = ProgramState.PROGRAM_STATE_MANAGEMENT_CATEGORY
                        }
                        // 2. 카테고리 선택
                        MainMenuItem.MAIN_MENU_CHOICE_CARTEGORY.itemNumber -> {
                            programState = ProgramState.PROGRAM_STATE_CHOICE_CATEGORY
                        }
                        // 3. 메모 내용 전체 보기
                        MainMenuItem.MAIN_MENU_SHOW_MEMO_ALL.itemNumber -> {
                            programState = ProgramState.PROGRAM_STATE_SHOW_MEMO_ALL
                        }
                        // 4. 종료
                        MainMenuItem.MAIN_MENU_ITEM_EXIT.itemNumber -> {
                            programState = ProgramState.PROGRAM_STATE_EXIT
                        }
                    }
                }

                // 비밀번호 입력 상태
                ProgramState.PROGRAM_STATE_INPUT_PASSWORD -> {
                    passwordClass.settingPassword()
                    programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                }

                // 카테고리 관리
                ProgramState.PROGRAM_STATE_MANAGEMENT_CATEGORY -> {
                    while (true){
                        // 카테고리 리스트 출력
                        printCategoryList()

                        // 카테고리 관리 메뉴 출력
                        var inputNumber = categoryManagementClass.selectCategoryManagementMenu()

                        when(inputNumber){
                            CategoryManagementItem.CATEGORY_INSERT.itemNumber -> {
                                categoryManagementClass.inputCategory()
                            }
                            CategoryManagementItem.CATEGORY_UPDATE.itemNumber -> {
                                categoryManagementClass.updateCategory()
                            }
                            CategoryManagementItem.CATEGORY_DELETE.itemNumber -> {
                                categoryManagementClass.deleteCategory()
                            }
                            CategoryManagementItem.CATEGORY_BEFORE.itemNumber -> {
                                programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                                break
                            }
                        }
                    }

                }

                // 카테고리 선택
                ProgramState.PROGRAM_STATE_CHOICE_CATEGORY -> {
                    // 카테고리 리스트 출력
                    printCategoryList()

                    // 선택한 카테고리의 번호
                    val inputCategory = memoManagementClass.choiceCategory()

                    // 카테고리가 0이면 이전으로 이동, 아니라면 메모 메뉴 출력
                    if (inputCategory == 0){
                        programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                    } else {
                        programState = ProgramState.PROGRAM_STATE_MEMO_MENU
                    }
                }

                // 메모 메뉴 선택
                ProgramState.PROGRAM_STATE_MEMO_MENU -> {
                    while (true){
                        // 메모 리스트 출력
                        memoManagementClass.printMemoList(memoManagementClass.choiceCategory)

                        // 메모 메뉴 출력
                        val inputNumber = memoManagementClass.memoManagement()

                        when(inputNumber){
                            // 메모 보기
                            MemoMenuItem.SHOW_MEMO.number -> {
                                memoManagementClass.showMemo()
                                if (memoManagementClass.choiceMemo == 0){
                                    continue
                                } else {
                                    memoManagementClass.printMemo()
                                }
                            }
                            // 메모 등록
                            MemoMenuItem.INSERT_MEMO.number -> {
                                memoManagementClass.insertMemo()
                            }
                            // 메모 수정
                            MemoMenuItem.UPDATE_MEMO.number -> {
                                memoManagementClass.updateMemo()
                            }
                            // 메모 삭제
                            MemoMenuItem.DELETE_MEMO.number -> {
                                memoManagementClass.deleteMemo()
                            }
                            // 이전으로 이동
                            MemoMenuItem.BEFORE_MEMO.number -> {
                                programState = ProgramState.PROGRAM_STATE_CHOICE_CATEGORY
                                break
                            }
                        }
                    }
                }

                // 메모 내용 전체 보기
                ProgramState.PROGRAM_STATE_SHOW_MEMO_ALL -> {
                    // 카테고리 리스트를 가져온다
                    getCategoryList()

                    for (cat in categoryList){
                        println("-------------------------------------")
                        println("$cat")
                        println("-------------------------------------")

                        memoManagementClass.getMemoList(cat)

                        if (memoManagementClass.memoList.size > 0){
                            for (m in memoManagementClass.memoList){
                                println()
                                println("제목 : ${m.title}")
                                println("내용 : ${m.content}")
                            }
                        } else {
                            println("등록된 메모가 없습니다.")
                        }
                    }
                    programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                }

                // 프로그램 종료
                ProgramState.PROGRAM_STATE_EXIT -> {
                    println()
                    println("프로그램을 종료합니다.")
                    exitProcess(0) // 0 : 정상 종료를 나타내는 코드
                }
            }
        }
    }

    // 카테고리 목록을 가져오는 메서드
    fun getCategoryList(){
        categoryList.clear()

        val dir = File(".")
        var fileList = dir.list()

        for (temp1 in fileList){
            if(temp1.endsWith(".cat")){
                val temp2 = temp1.replace(".cat", "")
                categoryList.add(temp2)
            }
        }
    }

    fun printCategoryList(){
        getCategoryList()

        println()
        println("---------- Category List ----------")

        if(categoryList.size == 0){
            println("등록된 카테고리가 없습니다.")
        } else {
            for (idx in 1 .. categoryList.size){
                println("$idx : ${categoryList[idx - 1]}")
            }
        }
    }
}

// 프로그램 상태를 나타내는 enum
enum class ProgramState{
    // 비밀번호 입력 상태
    PROGRAM_STATE_INPUT_PASSWORD,

    // 메인 메뉴를 보여주는 상태
    PROGRAM_STATE_SHOW_MENU,

    // 카테고리 관리
    PROGRAM_STATE_MANAGEMENT_CATEGORY,

    // 카테고리 선택
    PROGRAM_STATE_CHOICE_CATEGORY,

    // 메모 메뉴 선택
    PROGRAM_STATE_MEMO_MENU,

    // 메모 내용 전체 보기
    PROGRAM_STATE_SHOW_MEMO_ALL,
    // 종료
    PROGRAM_STATE_EXIT
}
