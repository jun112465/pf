(()=>{

    let btn_sign_up = document.getElementById("btn-sign-up");
    let error_msg = document.getElementById("error-msg");
    let form_id = document.getElementById("form-id");
    let form_pw = document.getElementById("form-pw");

    btn_sign_up.addEventListener("click", ()=>{
        console.log(form_id.value);
        console.log(form_pw.value);
        fetch("/members/new", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                'uid': form_id.value,
                'pwd': form_pw.value,
            })
        }).then(
            response=>{
                console.log("resp: ", response);
                response.json();
            }
        ).then(
            data => {
                console.log("data: ", data);
                if(data === undefined)
                    window.location.href = "/";

                error_msg.innerHTML = data.message;
                if(data == undefined)
                    window.location.href = "/";
                else {
                    console.log(data)
                    error_msg.innerHTML = data.message
                }
            }
        );
    })
})()