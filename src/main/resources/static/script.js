async function analyzeResume() {

    let file =
        document.getElementById("resumeFile").files[0];

    let jobDescription =
        document.getElementById("jobDescription").value;

    if (!file) {
        alert("Please select a resume file.");
        return;
    }

    if (!jobDescription.trim()) {
        alert("Please enter Job Description.");
        return;
    }

    let formData = new FormData();

    formData.append("file", file);
    formData.append("jobDescription", jobDescription);

    document.getElementById("result").innerHTML =
        "<h3>⏳ Analyzing Resume...</h3>";

    try {

        let response = await fetch(
            "http://localhost:8080/api/resume/upload",
            {
                method: "POST",
                body: formData
            }
        );

        let data = await response.json();

        document.getElementById("progressBar").style.width =
            data.score + "%";

        document.getElementById("progressBar").innerHTML =
            data.score + "%";

        let scoreColor = "#ef4444";

        if (data.score >= 80) {
            scoreColor = "#22c55e";
        }
        else if (data.score >= 50) {
            scoreColor = "#f59e0b";
        }

        document.getElementById("result").innerHTML =
        `
        <div class="result-card">

            <h2 style="color:${scoreColor}">
                🎯 ATS Score : ${data.score}
            </h2>

            <h3>
                ✅ Matched Skills :
                ${data.matchedSkills}
            </h3>

            <h3>
                ❌ Missing Skills :
                ${data.missingSkills || "None"}
            </h3>

            <h3>
                💡 Suggestions :
                ${data.suggestions}
            </h3>

        </div>
        `;

    }
    catch(error) {

        document.getElementById("result").innerHTML =
        `
        <h3 style="color:red;">
            Error while analyzing resume.
        </h3>
        `;

        console.log(error);
    }
}