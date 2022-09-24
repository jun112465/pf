// 즉시실행함수 IIDE
(()=>{
    let slideTitles = document.getElementsByClassName("slide-title")
    for(let i=0; i<slideTitles.length; i++){
        slideTitles[i].addEventListener('focus', ()=>{
            slideTitles[i].nextElementSibling.style.display = "inline-block";
        })
        slideTitles[i].addEventListener('focusout', ()=>{
            setTimeout(()=>{
                slideTitles[i].nextElementSibling.style.display = "none";
            }, 200);
        })
    }

    let newNotices = document.getElementsByClassName("new-item")
    for(let i=0; i<newNotices.length; i++){
        newNotices[i].addEventListener('click', ()=>{
            let category = newNotices[i].children.namedItem("category").value;
            console.log(category)
            createItem(category).then((value)=>{
                if(value){
                    location.assign('/');
                    alert("노트가 생성됐습니다.")
                }else{
                    alert("이미 생성된 노트가 있습니다.")
                }
            })
            console.log("clicked")
        })
    }
    // let newNotice = document.getElementById("new-notice")
    // newNotice.addEventListener('click', ()=>{
    //     let category = newNotice.children.namedItem("category").value;
    //     createItem(category).then((value)=>{
    //         if(value){
    //             location.assign('/');
    //             alert("노트가 생성됐습니다.")
    //         }else{
    //             alert("이미 생성된 노트가 있습니다.")
    //         }
    //     })
    //     console.log("clicked")
    // })

    let createItem = async (categoryId)=>{
        const resp = await fetch("/notice/add-note", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "categoryId" : categoryId
            }),
        }).then(result => result.json())
            .then(data => data)

        return resp;
    }

    //date format function
    function dateFormat(date) {
        let month = date.getMonth() + 1;
        let day = date.getDate();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;

        return date.getFullYear() + '-' + month + '-' + day;
    }

    //set contentEditable
    let notices = document.getElementsByClassName("notice")
    for (let i=0; i<notices.length; i++){
        let eb = notices[i].children[2]
        eb.setAttribute("contenteditable", "true");
        eb.addEventListener("input", ()=>{
            fetch("/notice/update", {
                method: "Post",
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    'id' : notices[i].children[0].value,
                    'memberId' : '',
                    'content' : eb.innerHTML,
                    'date' : ''
                })
            })
        })
    }

    // category 변경 기능
    let categories = document.getElementsByClassName("slide-title")
    for(let i=0; i<categories.length; i++){
        console.log("category id : " + categories[i].dataset.id)
        categories[i].addEventListener("input", ()=>{
            fetch("/notice/update-category", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    'id': categories[i].dataset.id,
                    'category': categories[i].innerText,
                })
            })
        })
    }


})()