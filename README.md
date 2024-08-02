### 저희가 지정한 웹을 크롤링 하는 프로그램입니다

자세한 구현 과정은 제 블로그에 있습니다. 

https://n-pureun.tistory.com/192

프로그램이   **@PostConstruct**을 통해 실행즉시 크롤링 기능을 수행합니다.

이는 단일 목적을 가진 프로그램이기에 실행버튼을 누른다는거 자체가 

웹 크롤링을 하겠다는 목적을 가졌기 때문이며, 서버 port를 잡지 않도록 하여

다른 프로그램과 병렬 실행이 가능하도록 하기 위함입니다.

## Class Crawler

```jsx
fun getCrawlData(idx: Int): WineData {
        val data = WineData()

        extractPrice(data)
        extractTypeCountryRegion(data)
        extractWineName(data)
        extractWineComponents(data)
        extractAroma(data)
        extractKind(data)
        extractStyle(data)

        data.originIdx = idx
        return data
    }
//이곳에서 각각의 함수를 불러와 DataCalss의 내용을 채워갑니다. 
//추가하고 싶은 데이터가 있다면 함수를 작성하고, 이곳에 등록해주셔야 합니다.
//각 함수가 작동하는 원리는 똑같고 내용만 다르기에 금방 적용 가능하실겁니다.
```

## Class FileStream

```jsx
var fileCnt=60
//fileCnt는 파일 뒤에 붙은 index를 의미합니다. 
//이곳에 원하는 값을 넣으시면 해당 값부터 카운트가 올라가며 파일이 저장됩니다.

File("/Users/t2023-m0097/Desktop/winds_data/wine_data_${fileCnt}.json")
//해당 클래스에서 크롤링 한 데이터를 저장하는 경로가 저의 컴퓨터 환경에 맞추어져 있습니다. 
//이것을 자신의 환경에 맞게 경로를 바꾸어 이용해 주세요.
```

## Class Manager

```jsx
 companion object{
        private val max=177327
        //우리가 어떤 idx부터 크롤링 했는지 기록해두기 위함입니다.
        private val now=161865
        //우리가 어떤 idx까지 진행했는지를 넣어주세요.
        private var cnt=5900
        //현재 파일로 쌓인 데이터의 개수입니다. 
        //1file=100data 입니다.
    }
    
 //이것을 알고 
 fun init()
 //을 본다면, 어떤 정책으로 프로그램이 동작하는지 이해할 수 있습니다.
```
