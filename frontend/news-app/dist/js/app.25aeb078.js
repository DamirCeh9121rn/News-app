(function(){"use strict";var t={5338:function(t,e,n){var a=n(6369),r=function(){var t=this,e=t._self._c;return e("div",{attrs:{id:"app"}},[e("NavBar"),e("div",{staticClass:"container"},[e("router-view")],1)],1)},o=[],i=function(){var t=this,e=t._self._c;return e("div",[e("nav",{staticClass:"navbar navbar-expand-lg navbar-light bg-light"},[e("div",{staticClass:"container-fluid"},[e("a",{staticClass:"navbar-brand",attrs:{href:"#"}},[t._v("RAF NEWS")]),t._m(0),e("div",{staticClass:"collapse navbar-collapse",attrs:{id:"navbarSupportedContent"}},[e("ul",{staticClass:"navbar-nav me-auto mb-2 mb-lg-0"},[e("li",{staticClass:"nav-item"},[e("router-link",{staticClass:"nav-link",class:{active:"News"===this.$router.currentRoute.name},attrs:{to:{name:"All-News"},tag:"a"}},[t._v("News")])],1),e("li",{staticClass:"nav-item"},[e("router-link",{staticClass:"nav-link",class:{active:"Popular"===this.$router.currentRoute.name},attrs:{to:{name:"Popular"},tag:"a"}},[t._v("Popular")])],1),e("b-dropdown",{staticClass:"e-auto mb-2 mb-lg-0 me-2",staticStyle:{height:"35px","margin-top":"5px"},attrs:{text:"Categories",variant:"secondary"}},t._l(t.categories,(function(n){return e("b-dropdown-item",{key:n.name,attrs:{href:"#"},on:{click:function(e){return t.getNewsByCategory(n.id)}}},[t._v(t._s(n.name))])})),1),t.canLogin?e("li",{staticClass:"nav-item"},[e("router-link",{staticClass:"nav-link",class:{active:"Login"===this.$router.currentRoute.name},attrs:{to:{name:"Login"},tag:"a"}},[t._v("Login")])],1):t._e(),t.canLogout?e("b-dropdown",{staticClass:"e-auto mb-2 mb-lg-0",staticStyle:{height:"35px","margin-top":"5px"},attrs:{text:"CMS",variant:"secondary"}},[e("b-dropdown-item",[e("router-link",{staticClass:"nav-link",class:{active:"CreateNews"===this.$router.currentRoute.name},attrs:{to:{name:"CreateNews"},tag:"a"}},[t._v("NEWS")])],1),e("b-dropdown-item",[e("router-link",{staticClass:"nav-link",class:{active:"CreateCategory"===this.$router.currentRoute.name},attrs:{to:{name:"CreateCategory"},tag:"a"}},[t._v("CATEGORIES")])],1),t.isAdmin?e("b-dropdown-item",[e("router-link",{staticClass:"nav-link",class:{active:"CreateUser"===this.$router.currentRoute.name},attrs:{to:{name:"CreateUser"},tag:"a"}},[t._v("USERS")])],1):t._e()],1):t._e()],1),t.canLogout?e("form",{staticClass:"d-flex",on:{submit:function(e){return e.preventDefault(),t.logout.apply(null,arguments)}}},[e("button",{staticClass:"btn btn-outline-secondary",attrs:{type:"submit"}},[t._v("Logout")])]):t._e()])])])])},s=[function(){var t=this,e=t._self._c;return e("button",{staticClass:"navbar-toggler",attrs:{type:"button","data-bs-toggle":"collapse","data-bs-target":"#navbarSupportedContent","aria-controls":"navbarSupportedContent","aria-expanded":"false","aria-label":"Toggle navigation"}},[e("span",{staticClass:"navbar-toggler-icon"})])}],u=(n(7658),n(1598)),l={name:"NavBar",computed:{canLogin(){return null==localStorage.getItem("jwt")||""===localStorage.getItem("jwt")},canLogout(){return"Login"!==this.$route.name&&null!=localStorage.getItem("jwt")},isAdmin(){const t=localStorage.getItem("jwt");if(null===t)return!1;const e=(0,u.Z)(t);return"admin"===e.role}},data(){return{selectedCategory:null,categories:[]}},mounted(){this.$axios.get("/api/categories?page=1").then((t=>{this.categories=t.data}))},methods:{logout(){localStorage.removeItem("jwt"),this.$router.push({name:"Login"})},getNewsByCategory(t){this.$router.push(`/news/by-category/${t}`).then((()=>{window.location.reload()}))}}},c=l,d=n(1001),p=(0,d.Z)(c,i,s,!1,null,"646b72e5",null),m=p.exports,g={components:{NavBar:m}},f=g,h=(0,d.Z)(f,r,o,!1,null,null,null),v=h.exports,b=(n(2801),n(2631));a["default"].use(b.ZP);const w=[{path:"/all-news",name:"All-News",meta:{authRequired:!1},component:()=>n.e(443).then(n.bind(n,5700))},{path:"/news/single-news-view/:id",name:"NewsDetails",meta:{authRequired:!1},component:()=>n.e(443).then(n.bind(n,7321))},{path:"/news/by-category/:id",name:"NewsByCategory",meta:{authRequired:!1},component:()=>n.e(443).then(n.bind(n,1316))},{path:"/news/popular",name:"Popular",meta:{authRequired:!1},component:()=>n.e(443).then(n.bind(n,7026))},{path:"/news/by-tag/:tagname",name:"NewsByTag",meta:{authRequired:!1},component:()=>n.e(443).then(n.bind(n,7876))},{path:"/users/login",name:"Login",meta:{authRequired:!1},component:()=>n.e(443).then(n.bind(n,9512))},{path:"/news",name:"CreateNews",meta:{authRequired:!0},component:()=>n.e(443).then(n.bind(n,7561))},{path:"/categories",name:"CreateCategory",meta:{authRequired:!0},component:()=>n.e(443).then(n.bind(n,8700))},{path:"/users/register",name:"CreateUser",meta:{authRequired:!0},component:()=>n.e(443).then(n.bind(n,1680))},{path:"/user/update/:id",name:"UpdateUser",meta:{authRequired:!0},component:()=>n.e(443).then(n.bind(n,7202))},{path:"/categories/:id",name:"UpdateCategory",meta:{authRequired:!0},component:()=>n.e(443).then(n.bind(n,8240))},{path:"/news/:id",name:"UpdateNews",meta:{authRequired:!0},component:()=>n.e(443).then(n.bind(n,268))}],C=new b.ZP({mode:"history",base:"/",routes:w});C.beforeEach(((t,e,n)=>{if(!0===t.meta.authRequired){const t=localStorage.getItem("jwt");if(!t)return void n({name:"Login"});const e=JSON.parse(atob(t.split(".")[1])),a=new Date(1e3*e.exp);if(a<new Date)return void n({name:"Login"})}n()}));var y=C,_=n(6162),k=n.n(_),S=n(4161);let R={baseURL:"http://localhost:8080"};const j=S.Z.create(R);j.interceptors.request.use((function(t){const e=localStorage.getItem("jwt");return t.headers.Authorization=`Bearer ${e}`,t}),(function(t){return Promise.reject(t)})),j.interceptors.response.use((function(t){return t}),(function(t){return t&&t.response&&401===t.response.status&&y.push({name:"Login"}),Promise.reject(t)})),Plugin.install=function(t){t.axios=j,window.axios=j,Object.defineProperties(t.prototype,{axios:{get(){return j}},$axios:{get(){return j}}})},a["default"].use(Plugin);Plugin;var x=n(6681),N=n(9425);n(7024);a["default"].use(x.XG7),a["default"].use(N.A7),a["default"].component("pagination",k()),a["default"].config.productionTip=!1,new a["default"]({router:y,render:t=>t(v)}).$mount("#app")}},e={};function n(a){var r=e[a];if(void 0!==r)return r.exports;var o=e[a]={exports:{}};return t[a].call(o.exports,o,o.exports,n),o.exports}n.m=t,function(){var t=[];n.O=function(e,a,r,o){if(!a){var i=1/0;for(c=0;c<t.length;c++){a=t[c][0],r=t[c][1],o=t[c][2];for(var s=!0,u=0;u<a.length;u++)(!1&o||i>=o)&&Object.keys(n.O).every((function(t){return n.O[t](a[u])}))?a.splice(u--,1):(s=!1,o<i&&(i=o));if(s){t.splice(c--,1);var l=r();void 0!==l&&(e=l)}}return e}o=o||0;for(var c=t.length;c>0&&t[c-1][2]>o;c--)t[c]=t[c-1];t[c]=[a,r,o]}}(),function(){n.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return n.d(e,{a:e}),e}}(),function(){n.d=function(t,e){for(var a in e)n.o(e,a)&&!n.o(t,a)&&Object.defineProperty(t,a,{enumerable:!0,get:e[a]})}}(),function(){n.f={},n.e=function(t){return Promise.all(Object.keys(n.f).reduce((function(e,a){return n.f[a](t,e),e}),[]))}}(),function(){n.u=function(t){return"js/about.a0064d7d.js"}}(),function(){n.miniCssF=function(t){}}(),function(){n.g=function(){if("object"===typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(t){if("object"===typeof window)return window}}()}(),function(){n.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)}}(),function(){var t={},e="news-app:";n.l=function(a,r,o,i){if(t[a])t[a].push(r);else{var s,u;if(void 0!==o)for(var l=document.getElementsByTagName("script"),c=0;c<l.length;c++){var d=l[c];if(d.getAttribute("src")==a||d.getAttribute("data-webpack")==e+o){s=d;break}}s||(u=!0,s=document.createElement("script"),s.charset="utf-8",s.timeout=120,n.nc&&s.setAttribute("nonce",n.nc),s.setAttribute("data-webpack",e+o),s.src=a),t[a]=[r];var p=function(e,n){s.onerror=s.onload=null,clearTimeout(m);var r=t[a];if(delete t[a],s.parentNode&&s.parentNode.removeChild(s),r&&r.forEach((function(t){return t(n)})),e)return e(n)},m=setTimeout(p.bind(null,void 0,{type:"timeout",target:s}),12e4);s.onerror=p.bind(null,s.onerror),s.onload=p.bind(null,s.onload),u&&document.head.appendChild(s)}}}(),function(){n.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})}}(),function(){n.p="/"}(),function(){var t={143:0};n.f.j=function(e,a){var r=n.o(t,e)?t[e]:void 0;if(0!==r)if(r)a.push(r[2]);else{var o=new Promise((function(n,a){r=t[e]=[n,a]}));a.push(r[2]=o);var i=n.p+n.u(e),s=new Error,u=function(a){if(n.o(t,e)&&(r=t[e],0!==r&&(t[e]=void 0),r)){var o=a&&("load"===a.type?"missing":a.type),i=a&&a.target&&a.target.src;s.message="Loading chunk "+e+" failed.\n("+o+": "+i+")",s.name="ChunkLoadError",s.type=o,s.request=i,r[1](s)}};n.l(i,u,"chunk-"+e,e)}},n.O.j=function(e){return 0===t[e]};var e=function(e,a){var r,o,i=a[0],s=a[1],u=a[2],l=0;if(i.some((function(e){return 0!==t[e]}))){for(r in s)n.o(s,r)&&(n.m[r]=s[r]);if(u)var c=u(n)}for(e&&e(a);l<i.length;l++)o=i[l],n.o(t,o)&&t[o]&&t[o][0](),t[o]=0;return n.O(c)},a=self["webpackChunknews_app"]=self["webpackChunknews_app"]||[];a.forEach(e.bind(null,0)),a.push=e.bind(null,a.push.bind(a))}();var a=n.O(void 0,[998],(function(){return n(5338)}));a=n.O(a)})();
//# sourceMappingURL=app.25aeb078.js.map