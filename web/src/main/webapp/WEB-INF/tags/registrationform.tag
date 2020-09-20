<div class="post">
    <form action="${pageContext.servletContext.contextPath}/mywall" method="post">
        <input type="text" name="name" placeholder="name" required>
        <input type="text" name="surname" placeholder="surname" required>
        <input type="text" name="patronymic" placeholder="patronymic">
        <select>
            <option value="" disabled selected>your gender</option>
            <option value="sex">male</option>
            <option value="sex">female</option>
        </select>
        <input type="email" name="email" placeholder="email" required>
        <input type="password" name="password" placeholder="password" required>
        <input type="password" name="confirmPassword" placeholder="confirm password" required>
        <input type="date" name="birthDate" placeholder="date of birth">
        <input type="text" name="icq" placeholder="icq">
        <input type="text" name="skype" placeholder="skype">
        <input type="text" name="country" placeholder="country" required>
        <input type="text" name="city" placeholder="city" required>
        <button>Register</button>
    </form>
</div>