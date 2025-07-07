class MemberListPage{
    constructor(){
        this.init();
    }
    init(){
        document.getElementById('searchBtn').addEventListener('click',()=>this.search());
        document.getElementById('showAllBtn').addEventListener('click',()=>this.renderAll());
        document.getElementById('searchInput').addEventListener('keyup',e=>{if(e.key==='Enter')this.search();});
        this.renderAll();
        this.updateStats();
    }
    renderAll(){
        fetch("http://localhost:8080/api/members")
            .then(r=>r.json())
            .then(d=>this.renderTable(d))
            .catch(()=>alert("목록 불러오기 실패"));
    }
    search(){
        const kw=document.getElementById('searchInput').value.trim();
        if(!kw) return;
        fetch(`http://localhost:8080/api/members/search?keyword=${encodeURIComponent(kw)}`)
            .then(r=>r.json())
            .then(d=>this.renderTable(d))
            .catch(()=>alert("검색 실패"));
    }
    renderTable(members){
        const c=document.getElementById('memberTableContainer');
        if(!members||members.length===0){
            c.innerHTML=`<div class="empty-state"><div style="font-size:4em;margin-bottom:20px;">👥</div><p>등록된 회원이 없습니다.</p></div>`;
            return;
        }
        c.innerHTML=`<table class="member-table">
      <thead><tr><th>이름</th><th>나이</th><th>성별</th><th>이메일</th><th>연락처</th><th>주소</th><th>가입일</th></tr></thead>
      <tbody>${members.map(m=>`<tr>
        <td>${m.name}</td><td>${m.age}세</td><td>${m.gender}</td>
        <td>${m.email}</td><td>${m.phone}</td><td>${m.address}</td><td>${m.joinDate}</td>
      </tr>`).join("")}</tbody></table>`;
    }
    updateStats(){
        fetch("http://localhost:8080/api/members/statistics")
            .then(r=>r.json())
            .then(d=>{
                totalMembers.textContent=d.totalMembers;
                maleMembers.textContent=d.maleMembers;
                femaleMembers.textContent=d.femaleMembers;
                avgAge.textContent=d.avgAge.toFixed(1);
            })
            .catch(()=>alert("통계 로딩 실패"));
    }
}
new MemberListPage();
