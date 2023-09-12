(()=>{

    let form = document.getElementById("sign-form");
    let btn_sign_in = document.getElementById("btn-sign-in");
    let error_msg = document.getElementById("error-msg");
    let form_id = document.getElementById("form-id");
    let form_pw = document.getElementById("form-pw");

    btn_sign_in.addEventListener("click", ()=>{
        console.log("clicked");
        fetch("/members/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                'uid': form_id.value,
                'pwd': form_pw.value,
            })
        }).then((data)=>{
            console.log(data)
        });
    })
})()