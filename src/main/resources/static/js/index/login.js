(()=>{

    let form = document.getElementById("sign-form");
    let btn_sign_up = document.getElementById("btn-sign-up");
    let error_msg = document.getElementById("error-msg");
    let form_id = document.getElementById("form-id");
    let form_pw = document.getElementById("form-pw");

    // btn_sign_up.addEventListener("click", ()=>{
    //     console.log("hello");
    //     form.action = "/members/new";
    //     form.submit()
    // })

    btn_sign_up.addEventListener("click", ()=>{
        console.log("clicked");
        // fetch("/members/new", {
        //     method: "POST",
        //     headers: {"Content-Type": "application/json"},
        //     body: JSON.stringify({
        //         'memberId': form_id.innerText,
        //         'password': form_pw.innerText,
        //     })
        // }).then(
        //     response => response.json()
        // ).then(
        //     data => {
        //         console.log(data);
        //         error_msg.innerText = "duplicated id";
        //     }
        // );
    })



})()