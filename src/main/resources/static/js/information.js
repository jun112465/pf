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

for (let i in categoryUpdateBtns) {
    let updateBtn = categoryUpdateBtns[i];
    let deleteBtn = categoryDeleteBtns[i];
    let categoryId = categoryIds[i];
    let newName = newCategoryNames[i];
    updateBtn.addEventListener('click', ()=>{
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
    deleteBtn.addEventListener('click', ()=>{
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

