(()=>{

    let form = document.getElementById("sign-form");
    let btn_sign_in = document.getElementById("btn-sign-in");
    let btn_sigh_up = document.getElementById("btn-sign-up");

    btn_sigh_up.addEventListener("click", ()=>{
        console.log("hello");
        form.action = "/members/new";
        form.submit()
    })



})()