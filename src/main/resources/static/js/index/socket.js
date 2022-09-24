(()=>{
    let ws;
    let userName;
    let sendBtn;
    $(document).ready(()=>{
        // 랜덤이기 때문에 결과값이 다를 수 있음.
        userName = Math.random().toString(36).substr(2,11); // "twozs5xfni";
        sendBtn = document.getElementById("sendBtn");
        sendBtn.addEventListener("click", send);
        wsOpen();
    })

    //웹소켓을 생성한 후 서버와 연결시킨다.
    //웹소켓에 이벤트 리스너를 연결해준다.
    function wsOpen(){
        let host = "3.39.191.71:8080"
        console.log(location.host)
        //WebSocketConfig 에서 설정한 path와 동일한 값을 줘야한다.
        ws = new WebSocket("ws://"+location.host+"/ws/chat");
        wsEvt();
    }

    function wsEvt(){
        ws.onopen = function (data){
            alert("서버와 연결되었습니다")
            //소켓이 열리면 초기화 세팅하기
            // let str = userName + ": 님이 입장하셨습니다.";
            // ws.send(str);
        }

        // 서버에서 데이터를 보낼 떄
        ws.onmessage = function(data){
        }

        // 소켓 통신을 끊을 때
        ws.onclose = ()=>{
            let str = userName + ": 님이 방을 나가셨습니다.";
            ws.send(str);
        }

        // document.addEventListener("keypress", e=>{
        //     if(e.key == "Enter") //enter key
        //         send();
        // })
    }

    //메세지 전송 함수 - 버튼클릭 리스너
    function send(){
        let msg = $("#msg").val();
        let sendMsg = userName + " : " + msg + "\n";
        console.log(sendMsg);
        ws.send(sendMsg);

        $("#msg").val("");
        $("#msg").focus();
    }

})()