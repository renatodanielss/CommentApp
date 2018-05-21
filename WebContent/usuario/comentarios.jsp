<%@page%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ page import="br.fatec.model.Usuario" %>

<%
	Usuario usuario = (Usuario)session.getAttribute("user");
	request.setAttribute("usuario", usuario.getLogin());
%>

<t:genericpage>
<jsp:attribute name="header">
<title>Comentários</title>
<script>
	var indexEdit;
	var comment;
	var editar = false;
	var app = angular.module('commentApp', []).controller(
			'commentController',
			function($scope, $http) {
				$http.get('http://localhost:3000/api/comments').then(
					function(response) {
						$scope.comments = response.data;
					});
				
					$scope.isUsuario = function(usuarioComment) {
					    var usuarioSessao = '${usuario}';
					    if (usuarioComment == usuarioSessao){
					    	return true;
					    }
					    else{
					    	return false;
					    }
					}
				
					$scope.editComment = function(i) {
						if (indexEdit == i){
							return true;
						}
						return false;
					}
					
					$scope.getComment = function(i, comentario) {
						if (indexEdit == i){
							return comentario;
						}
						return "";
					}
					
					$scope.getIndex = function(i, comentario) {
						indexEdit = i;
						comment = comentario;
						editar = true;
					}
					
					$scope.getEditar = function(i) {
						if (indexEdit == i)
							return editar;
						return false;
					}
					
					$scope.cancelar = function(i, comentario) {
						if (indexEdit == i){
							editar = false;
							indexEdit = null;
							comment = comentario;
						}
					}
			});
</script>
</jsp:attribute>
<jsp:body>
<br>
	<div ng-app="commentApp" ng-controller="commentController" class="commentApp">
		<div ng-repeat="x in comments.data" style="margin:0 auto; overflow: auto; background-color: #88f986; margin-bottom: 10px;">
			<label id="lblUsuario" class="comment" style="background-color: #88f986; width: 170px;">{{
			x.usuario }}<br style="padding-bottom: 10px;">
			{{ x.datahora | date: 'HH:mm dd/MM/yyyy' }}
			</label>
			<div style="float: left; background-color: #bdf3ef; white-space: nowrap;">
				<div ng-if="!editComment($index)" ng-model="divComment">
					<label name="lblComment" ng-model="lblComentario" id="lblComment" class="comment" style="white-space: pre-line;">{{
					x.comentario }}
					</label>
				</div>
				<div ng-if="editComment($index)" ng-model="divEdit" style="border: 0px;">
					<form action="/editComment" method="post">
						<textarea ng-model="editComentario" name="editComentario" id="editComentario" ng-value="getComment($index, x.comentario)" class="editComentario" placeholder="Edite o seu comentário"></textarea><br>
						<input type="hidden" name="id" value="{{ x.id }}"></input>
						<input type="submit" value="Salvar" class="botaoComentar"></input>
						<input type="button" value="Cancelar" ng-click="cancelar($index)" class="botaoComentar" style="float: left; clear: both;"></input>
					</form>
				</div>
				<div ng-if="isUsuario(x.usuario) && !getEditar($index)">
					<input type="image" alt="Editar" src="/resources/images/edit.png" class="icons" ng-click="getIndex($index, x.comentario)"></input>
					<form action="/deleteComment" method="post" style="border: 0;">
						<input type="hidden" name="id" value="{{ x.id }}"></input>
						<input type="image" alt="Deletar" src="/resources/images/delete.png" class="icons"></input>
					</form>
				</div>
			</div>
		</div>
	</div>
	<br style="clear: both;">
	<div align="center">
		<form action="/createComment" method="post">
			<textarea ng-model="comentario" name="comentario" class="novoComentario" placeholder="Digite o seu comentário"></textarea><br>
			<input type="submit" value="Comentar" class="botaoComentar"></input>
		</form>
	</div>
</jsp:body>
</t:genericpage>