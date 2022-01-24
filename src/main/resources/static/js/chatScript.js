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
    console.log(location.host)
    //WebSocketConfig 에서 설정한 path와 동일한 값을 줘야한다.
    ws = new WebSocket("ws://"+location.host+"/ws/chat");
    wsEvt();
}
//
function wsEvt(){
    ws.onopen = function (data){
        //소켓이 열리면 초기화 세팅하기
        // let str = userName + ": 님이 입장하셨습니다.";
        // ws.send(str);
    }

    ws.onmessage = function(data){
        console.log(data);
        let msg = data.data;
        if(msg != null && msg.trim() != ""){
            // $("#chatBox").append("<p>",msg,"</p>");
            let div1 = document.createElement("div");
            div1.className="d-flex justify-content-start mb-4"

            let div2 = document.createElement("div");
            div2.className="img_cont_msg"

            let img = document.createElement("img");
            img.src = "https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg";
            img.className="rounded-circle user_img_msg"

            let div3 = document.createElement("div");
            div3.className="msg_cotainer";

            let span = document.createElement("span");
            span.className="msg_time";
            span.innerText = "8:40 AM, Today";

            div2.append(img);
            div1.append(div2);
            div3.innerText=msg;
            div3.append(span);
            div1.append(div3);
            $("#chatBox").append(div1);
        }

        // <div className="d-flex justify-content-start mb-4">
        //     <div className="img_cont_msg">
        //         <img src="https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg"
        //              className="rounded-circle user_img_msg">
        //     </div>
        //     <div className="msg_cotainer">
        //         Hi, how are you samim?
        //         <span className="msg_time">8:40 AM, Today</span>
        //     </div>
        // </div>
    }

    ws.onclose = ()=>{
        let str = userName + ": 님이 방을 나가셨습니다.";
        ws.send(str);
    }

    // document.addEventListener("keypress", e=>{
    //     if(e.key == "Enter") //enter key
    //         send();
    // })
}

let stringToHTML = function (str) {
    let parser = new DOMParser();
    let doc = parser.parseFromString(str, 'text/html');
    return doc.body;
};

//닉네임을 설정해주고 input 버튼을 누르면 웹소켓에 연결되고 닉네임이 설정된다.
function chatName(){
    userName = $("#userName").val();
    console.log(userName);
    if(userName == null || userName.trim() == "") {
        alert("사용자 이름을 입력해주세요")
        $("userName").focus();
    } else{
        console.log("webSocket opened");
        wsOpen();
    }
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
