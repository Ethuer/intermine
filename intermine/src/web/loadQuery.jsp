<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!-- loadQuery.jsp -->
<c:if test="${!empty SAVED_QUERIES}">
  <html:form action="/loadQuery">
    <html:select property="queryName">
      <c:forEach items="${SAVED_QUERIES}" var="entry">
        <html:option value="${entry.key}"/>
      </c:forEach>
    </html:select>
    <html:submit property="action">
      <fmt:message key="query.load"/>
    </html:submit>
  </html:form>
</c:if>
<!-- /loadQuery.jsp -->
