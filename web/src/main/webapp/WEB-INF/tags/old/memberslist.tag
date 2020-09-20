<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
    <c:forEach var="member" items="${members}">
        <li>
            <div>
                    ${member.getKey().name}
                <ul>
                    <c:forEach var="phone" items="${member.getValue()}">
                        <li>
                                ${phone.number}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </li>
        ------------------------------------------------
    </c:forEach>
</ul>