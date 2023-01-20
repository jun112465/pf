(()=>{

    let btn_sign_up = document.getElementById("btn-sign-up");
    let error_msg = document.getElementById("error-msg");
    let form_id = document.getElementById("form-id");
    let form_pw = document.getElementById("form-pw");

    btn_sign_up.addEventListener("click", ()=>{
        fetch("/members/new", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                'memberId': form_id.value,
                'password': form_pw.value,
            })
        }).then(
            response => response.json()
        ).then(
            data => {
                console.log(data);
                error_msg.innerHTML = data.message;
            }

        );
    })
})()