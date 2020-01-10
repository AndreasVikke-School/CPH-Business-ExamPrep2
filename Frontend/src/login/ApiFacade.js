//const URL = "http://localhost:8080/ExamPrep2"; // Local
const URL = "https://andreasvikke.dk/ExamPrep2" //Deploy

function handleHttpErrors(res) {
    if (!res.ok) {
        return Promise.reject({ status: res.status, fullError: res.json() })
    }
    return res.json();
}

function ApiFacade() {
    //Insert utility-methods from a latter step (d) here
    const setToken = (token) => {
        localStorage.setItem('jwtToken', token)
    }
    const getToken = () => {
        return localStorage.getItem('jwtToken')
    }
    const loggedIn = () => {
        const loggedIn = getToken() != null;
        return loggedIn;
    }
    const logout = () => {
        localStorage.removeItem("jwtToken");
    }
    
    const makeOptions = (method, addToken, body) => {
        var opts = {
            method: method,
            headers: {
                "Content-type": "application/json",
                'Accept': 'application/json',
            }
        }
        if (addToken && loggedIn()) {
            opts.headers["x-access-token"] = getToken();
        }
        if (body) {
            opts.body = JSON.stringify(body);
        }
        return opts;
    }

    const login = (user, pass) => {
        const options = makeOptions("POST", true, { username: user, password: pass });
        return fetch(URL + "/api/login", options)
            .then(handleHttpErrors)
            .then(res => { setToken(res.token) })
    }

    const fetchUser = () => {
        const options = makeOptions("GET", true); //True add's the token
        return fetch(URL + "/api/info/user", options).then(handleHttpErrors);
    }

    const fetchJokeByCategory = (categories) => {
        return fetch(URL + "/api/jokes/jokeByCategory/" + categories, makeOptions("GET")).then(handleHttpErrors);
    }
    const fetchJokeByCategoryV2 = (categories) => {
        return fetch(URL + "/api/jokes/jokeByCategoryV2/" + categories, makeOptions("GET", true)).then(handleHttpErrors);
    }

    const fetchAllCategories = () => {
        return fetch(URL + "/api/category/all", makeOptions("GET")).then(handleHttpErrors);
    }

    const fetchRequestCountByCategory = (category) => {
        return fetch(URL + "/api/category/categoryCount/" + category, makeOptions("GET", true)).then(handleHttpErrors);
    }

    return {
        login,
        logout,
        fetchUser,
        fetchJokeByCategory,
        fetchJokeByCategoryV2,
        fetchAllCategories,
        fetchRequestCountByCategory
    }

}

export default ApiFacade();