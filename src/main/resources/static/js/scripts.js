/*!
* Start Bootstrap - Freelancer v7.0.5 (https://startbootstrap.com/theme/freelancer)
* Copyright 2013-2021 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-freelancer/blob/master/LICENSE)
*/

$(document).ready(function() {
    //여기 아래 부분
    $('#summernote').summernote({
        height: 300,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
        callbacks: {	//여기 부분이 이미지를 첨부하는 부분
            onImageUpload : function(files) {
                uploadSummernoteImageFile(files[0],this);
            },
            onPaste: function (e) {
                let clipboardData = e.originalEvent.clipboardData;
                if (clipboardData && clipboardData.items && clipboardData.items.length) {
                    let item = clipboardData.items[0];
                    if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                        e.preventDefault();
                    }
                }
            }
        }
    });



    /**
     * 이미지 파일 업로드
     */
    function uploadSummernoteImageFile(file, editor) {
        let data = new FormData();
        data.append("file", file);
        $.ajax({
            data : data,
            type : "POST",
            url : "/upload",
            contentType : false,
            processData : false,
            success : function(data) {
                console.log(data);
                //항상 업로드된 파일의 url이 있어야 한다.
                $(editor).summernote('insertImage', data.url);
            }
        });
    }

    $("div.note-editable").on('drop',function(e){
        for(i=0; i< e.originalEvent.dataTransfer.files.length; i++){
            uploadSummernoteImageFile(e.originalEvent.dataTransfer.files[i],$("#summernote")[0]);
        }
        e.preventDefault();
    })

});

window.addEventListener('DOMContentLoaded',
    event => {

        // Navbar shrink function
        var navbarShrink = function () {
            const navbarCollapsible = document.body.querySelector('#mainNav');
            if (!navbarCollapsible) {
                return;
            }
            if (window.scrollY === 0) {
                navbarCollapsible.classList.remove('navbar-shrink')
            } else {
                navbarCollapsible.classList.add('navbar-shrink')
            }

        };

        // Shrink the navbar
        navbarShrink();

        // Shrink the navbar when page is scrolled
        document.addEventListener('scroll', navbarShrink);

        // Activate Bootstrap scrollspy on the main nav element
        const mainNav = document.body.querySelector('#mainNav');
        if (mainNav) {
            new bootstrap.ScrollSpy(document.body, {
                target: '#mainNav',
                offset: 72,
            });
        }
        ;

        // Collapse responsive navbar when toggler is visible
        const navbarToggler = document.body.querySelector('.navbar-toggler');
        const responsiveNavItems = [].slice.call(
            document.querySelectorAll('#navbarResponsive .nav-link')
        );
        responsiveNavItems.map(function (responsiveNavItem) {
            responsiveNavItem.addEventListener('click', () => {
                if (window.getComputedStyle(navbarToggler).display !== 'none') {
                    navbarToggler.click();
                }
            });
        });

        let closeScheduleBtns = document.getElementsByClassName("btn-closeSchedule");
        for (let i = 0; i < closeScheduleBtns.length; i++) {
            closeScheduleBtns[i].addEventListener("click", (e) => {
                let id = closeScheduleBtns[i].nextElementSibling.value;
                let data = new FormData();
                data.append("id",id);
                console.log(data);
                $.ajax({
                    data:data,
                    type: "post",
                    url: "schedule/delete",
                    contentType : false,
                    processData : false,
                    success: function (data) {
                        // location.href="/";
                        location.reload();
                        location.href="/#schedule-list";
                    }
                })
            })
        }
    });

function logout(){
    let date = new Date();
    date.setDate(date.getDate() - 100);
    let Cookie = `memberId=; Expires=${date.toUTCString()}`;
    document.cookie = Cookie;

    location.reload();
}

function submitNotice(){
    let content = document.getElementById("summernote").innerText;
    let title = document.getElementById("summernote-title").innerText;
    let data = new FormData();
    data.append("content", content);
    data.append("title", title);
    $.ajax({
        data : data,
        type : "POST",
        url : "/notice/add-note",
        contentType : false,
        processData : false,
        success : function(data) {
            console.log(data);
            //항상 업로드된 파일의 url이 있어야 한다.
            // $(editor).summernote('insertImage', data.url);
        }
    });
}


function addFriend(){
    let friendId = document.getElementById("input-friendId").value;
    let data = new FormData();
    data.append("memberId", friendId);
    $.ajax({
        data : data,
        type : "POST",
        url : "/members/add-friend",
        contentType : false,
        processData : false,
        success : function(data) {
            // console.log(data);
            $("#input-friendId").val("");
            reloadFriendList(data);
            //항상 업로드된 파일의 url이 있어야 한다.
            // $(editor).summernote('insertImage', data.url);
        }
    });
}

function sendFriendMemberId(id){
    $.ajax({
        data : {
            memberId : id
        },
        type : "GET",
        url : "/schedule/sendFriendSchedule",
        success : function(data){
            console.log(data);
            reloadSchedule(data);
        }
    });

    function reloadSchedule(data){
        let schedule = document.getElementById("schedule-container");
        schedule.innerText = "";
        schedule.innerHTML = "";
        for(let element of data){
            console.log(element);

            let div1 = document.createElement("div");
            let div2 = document.createElement("div");
            let div3 = document.createElement("div");
            let div4 = document.createElement("div");
            let div5 = document.createElement("div");
            let btn = document.createElement("button");
            let input = document.createElement("input");

            div1.className = "col-md-6 col-lg-4 mb-5";
            div2.className = "portfolio-item mx-auto";
            div3.className = "modal-header border-0";
            div4.innerText = element.title;
            div5.className="schedule-content";
            div5.innerHTML = element.contents;

            btn.className = "btn-closeSchedule btn-close";
            btn.ariaLabel = "Close";
            input.type = "hidden";
            input.value = element.id;

            div1.append(div2);
            div2.appendChild(div3);
            div3.appendChild(div4);
            div3.appendChild(btn);
            div3.appendChild(input);
            div2.appendChild(div5);

            schedule.append(div1);
        }
    }
}

function reloadFriendList(data){
    let listDiv = document.getElementById("friend-list");
    listDiv.innerHTML = "";
    let keys = Object.keys(data);
    for(let i=0; i<keys.length; i++){
        let key = Number(keys[i]);
        let value = data[key];
        let tag = document.createElement("a");
        tag.className="list-group-item list-group-item-action";
        tag.addEventListener("click", ()=>{
            sendFriendMemberId(key);
        });
        tag.innerText=key + " : " + value;
        listDiv.append(tag);
    }
}

function deleteSchedule(e) {

    console.log("deleteSchedule function executed");
    let data = new FormData();
    data.append("id",id);
    $.ajax({
        data:data,
        type: "post",
        url: "schedule/delete",
        contentType : false,
        processData : false,
        success: function (data) {
            if(data.redirect){
                window.location.href = data.redirect;
            }
        }
    })
};

