(()=> {

    // convert htmlText to Html at Start
    let contents = document.getElementsByClassName("content");
    let targets = document.getElementsByClassName("target");
    for (let i = 0; i < contents.length; i++) {
        // target 의 값이 먼저 바뀌어야한다.
        targets[i].innerHTML = contents[i].textContent;
        contents[i].innerHTML = contents[i].textContent;
    }

    let addPostBtn = document.getElementById("postAddBtn");
    // addPostBtn 특정 상황에서 없애기.
    let url = new URL(window.location.href);
    let categoryId = url.searchParams.get("categoryId")
    if(categoryId==null)
        addPostBtn.style.display = 'none';

    // add post btn eventListener
    addPostBtn.addEventListener('click', ()=>{
        // from url
        let url = new URL(window.location.href);
        let categoryId = url.searchParams.get("categoryId")

        // from input tag
        let userId = document.getElementById("userId");

        // fetch & reload
        fetch("/post/add", {
            method : "POST",
            headers: { 'Content-Type': 'application/json' },
            body : JSON.stringify({
                'categoryId' : categoryId,
                'userId' : userId.value,
            })
        })
            .then(response=>response.text())
            .then(data => {
                console.log(data)
                location.href = location.origin + data;
            })


    })

    // add edit & save button eventListener
    let posts = document.getElementsByClassName("post");
    let editors = document.getElementsByClassName("editor");
    let btnEditAndSave = document.getElementsByClassName("btn_edit_or_save");
    let commentBoxes = document.getElementsByClassName("comment-box");
    let editBoxes = document.getElementsByClassName("edit-box");
    for (let i = 0; i < btnEditAndSave.length; i++) {
        let content = contents[i];
        let editor = editors[i];
        let btn = btnEditAndSave[i];
        let post = posts[i];
        let commentBox = commentBoxes[i];

        btn.addEventListener("click", () => {
            if (btn.value == "edit") {
                btn.value = "save"
                content.style.display = "none"
                editor.style.display = "flex"
                commentBox.style.display = "none";

                post.style.width = "800px"
                post.style.minWidth = "800px"

            } else {
                btn.value = "edit"
                content.style.display = "block"
                editor.style.display = "none"
                commentBox.style.display = "block";


                // targets[i].innerHTML = contents[i].textContent;
                post.style.width = "400px"
                post.style.minWidth = "400px"
            }
        })
    }

    // editing 실시간 변화
    let converter = new showdown.Converter();
    converter.setFlavor('github');
    for (let i = 0; i < editors.length; i++) {
        let content = contents[i];
        let postId = posts[i].children[0].value;
        // let editBox = editors[i].children[0];
        let editBox = editBoxes[i];
        let target = targets[i];
        let loading = false

        editBox.setAttribute("contenteditable", "true");
        editBox.addEventListener("input", () => {
            // convert to html
            target.innerHTML = converter.makeHtml(editBox.value);
            content.innerHTML = converter.makeHtml(editBox.value);

            // autoSave
            if (!loading){
                setTimeout(()=>{
                    fetch("/post/update", {
                        method: "Post",
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({
                            'id' : postId,
                            'content' : editBox.value,
                        })
                    })
                    loading = !loading
                    console.log("Note Saved!")
                }, 1500)
                loading = !loading
            }
        })
    }


    // allPost 화면일 때 postAddBtn 없애기
    if(location.search == ''){
        document.getElementById("postAddBtn").style.display = "none";
    }


    // post delete btn add eventListener
    function deleteConfirm(postId){
        let url = new URL(window.location.href);
        let categoryId = url.searchParams.get("categoryId")
        let pageNo = url.searchParams.get("pageNo")
        let params = {
            'id' : postId,
            'pageNo' : pageNo,
            'pageCategory' : categoryId
        }

        console.log("params", params)
        let shouldDelete = confirm("삭제하시겠습니까?")
        if(shouldDelete) {
            // fetch("/post/delete", {
            //     method: "Post",
            //     headers: { 'Content-Type': 'application/json' },
            //     body: JSON.stringify({
            //         'id' : postId,
            //         'pageNo' : pageNo,
            //         'pageCategory' : categoryId
            //     })
            // }).then(resp => {
            //     resp.text()
            // }).then(data => {
            //     console.log(data)
            // })
            fetch("/post/delete", {
                method : "POST",
                headers: { 'Content-Type': 'application/json' },
                body : JSON.stringify(params)
            })
                .then(response=>response.text())
                .then(data => {
                    console.log(data)
                    location.href = location.origin + "/post/get" + data;
                })
        }
    }

    let postDeleteBtns = document.getElementsByClassName("postDeleteBtn");

    for(let i in postDeleteBtns){
        let postDltBtn = postDeleteBtns[i];
        let postId = postDltBtn.dataset.postid;
        postDltBtn.addEventListener('click', ()=>{
            deleteConfirm(postId)
        });
    }



})();



