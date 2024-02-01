let changePwBtn = document.getElementById("changePwBtn");
changePwBtn.addEventListener('click', ()=>{
    console.log("clicked")
    let currentPw = document.getElementById("currentPassword").value;
    let newPw = document.getElementById("newPassword").value;
    let confirmPw = document.getElementById("confirmPassword").value;
    fetch("/users/update-password", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            "currentPassword" : currentPw,
            "newPassword" : newPw,
            "confirmPassword" : confirmPw,
        })
    }).then(json=>{
        console.log(json)
        location.reload();
    });
})

// adding category update & delete button eventListener
let categoryUpdateBtns = document.getElementsByClassName("categoryUpdateBtn");
let categoryDeleteBtns = document.getElementsByClassName("categoryDeleteBtn");
let categoryIds = document.getElementsByClassName("categoryId");
let newCategoryNames = document.getElementsByClassName("newCategoryName");

for (let i=0; i<categoryUpdateBtns.length; i++) {
    let categoryId = categoryIds[i];
    let newName = newCategoryNames[i];
    categoryUpdateBtns[i].addEventListener('click', ()=>{
        fetch("/category/update", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                "id" : categoryId.value,
                "name" : newName.value
            })
        }).then(json=>{
            console.log(json)
            location.reload();
        });
    })
    categoryDeleteBtns[i].addEventListener('click', ()=>{
        fetch("/category/delete", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                "id" : categoryId.value,
            })
        }).then(json=>{
            console.log(json)
            location.reload();
        });
    })
}


console.log("helo");
let userId = document.getElementById("userId")
let resignPassword = document.getElementById("resignPassword");
let accountDltBtn = document.getElementById("accountDltBtn");
accountDltBtn.addEventListener('click', ()=>{
    console.log("clicked");
    console.log(userId.value)
    fetch("/users/delete", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            "userId" : userId.value,
            "password" : resignPassword.value
        })
    }).then(json=>{
        alert("User Resigned");
        location.href = "/";
    });
})


