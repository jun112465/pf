let changeUserIdBtn = document.getElementById("change-id-btn");
changeUserIdBtn.addEventListener('click', ()=>{
    console.log("clicked")
    let userId = document.getElementById("user-id").value
    fetch("/users/update", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            'userId': userId,
        })
    }).then(()=>{
        location.reload();
    });
})


