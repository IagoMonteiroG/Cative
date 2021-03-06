<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
	
	import="br.com.cative.dao.UsuarioDAO"
	import="br.com.cative.beans.Usuario"     
%>
<%
	String email = request.getParameter("email");
 	String nome = "";
 	String tema = "";
 	int pontos = 0;
 	int id = 0;
 	String foto = "";
 	int qtdMissoes = 0;
 	String senha = "";

 	
	if(email != null) {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = dao.getUsuarioByEmail(email);	
		nome = usuario.getNome() == null ? "" : usuario.getNome();
		tema = usuario.getTema();
		pontos = usuario.getPontuacao();
		id = usuario.getIdUsuario();
		foto = usuario.getFoto();
		senha = usuario.getSenha();
		qtdMissoes = dao.getQuantidadeMissoesConcluidas(id);
	}
	
%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Perfil <% out.print(nome); %></title>
  <link rel="stylesheet" href="./assets/css/main.min.css">
</head>

<body>
			<% 
				HttpSession sessionUsuario = request.getSession(true);
				Integer idUsuario = (Integer) sessionUsuario.getAttribute("idUsuario");
				int tipoUsuario = (int)sessionUsuario.getAttribute("tipoUsuario");
				sessionUsuario.setAttribute("corAluno", tema);
			%>
			<%
				if(tipoUsuario == 2) {
			%>
			  <jsp:include page="components/navegacao-aluno.jsp"></jsp:include>
			<%
				} else {
				request.setAttribute("corAluno", tema);
			%>
			  <jsp:include page="components/navegacao-professor.jsp"></jsp:include>
		  	<%
				}
		  	%>
			
  <form action="${pageContext.request.contextPath}/AtualizaPerfil" method="POST">
  	  <input type="hidden" value="<% out.print(id); %>" name="id_usuario"/>
  	  
	  <div class="perfil jsPerfil">
	    <div class="perfil__capa">
	      <div class="perfil__cor-capa jsCorPerfil" style=" background-color: <% out.println(tema); %>"></div>
	      <div class="perfil__regiao-foto">
	        <div class="perfil__foto-wrap jsCorPerfil" style=" background-color: <% out.println(tema); %>">
	          <textarea class="perfil__capa-foto-textarea jsTextAreaDataFoto" name="foto_perfil">
	          <% 
		          foto = (foto == "" || foto == null) ? "assets/img/foto-aluno.png" : foto;
		          out.println(foto);
		       	%>
	          </textarea>
	          <img src="<% out.println(foto); %>" class="jsFotoPerfil" alt="foto de perfil">
	          <span class="trocar-foto jsTrocarFotoPerfil">
	            <input type="file" class="perfil__input-file jsInputFotoPerfil" accept='image/*'>
	            <span class="trocar-foto__icone">
	            </span>
	          </span>
	        </div>
	        <div class="perfil__nome jsNomePerfil">
	          <input type="hidden" name="nome" value="<% out.print(nome); %>"/>
	          <% out.print(nome); %>
	        </div>
	      </div>
	    </div>
	    <div class="estrelas">
	      <span class="estrelas__numero">
	        <% out.println(pontos); %> 
	      </span>
	      <span class="estrelas__estrela"></span>
	    </div>
	    <div class="missoes-concluidas">
	      <span class="missoes-concluidas__texto">
	        Miss�es conclu�das:
	      </span>
	      <span class="missoes-concluidas__numero">
	        <% out.print(qtdMissoes); %>
	      </span>
	      <span class="missoes-concluidas__medalha"></span>
	    </div>
	
	    <div class="perfil__missoes">
	      <div class="perfil-section-title">
	        <h3>
	          Miss�es do aluno
	        </h3>
	      </div>
	      <div class="missoes">
	
	                <%@ page import="br.com.cative.dao.*" %>
	                <%@ page import="br.com.cative.beans.*" %>
	                <%@ page import="java.util.List" %>
	
	                <%
		         		MissaoDAO missaoDAO = new MissaoDAO();
		                List<Missao> missoes = missaoDAO.getMissoes(idUsuario);
		        		
		        		for(Missao m: missoes) {	        	
		        			out.println("<div class='card-missao jsCardMissao'");
		        				out.println("data-cod='"+m.getIdMissao()+"' data-descricao='"+m.getDescricao()+"' data-titulo='"+m.getObjetivo()+"' data-estrelas='"+m.getPontos()+"'>");
			        			out.println("<div class='card-missao__imagem'>");
			        				out.println("<img src='./assets/img/missao-mochila.png' alt='Imagem da miss�o'>");
			        			out.println("</div>");
			        			out.println("<div class='card-missao__body'>");
				        			out.println("<div class='card-missao__titulo'>");
				        				out.println("<h3>"+m.getObjetivo()+"</h3>");
			        				out.println("</div>");
			        				out.println("<div class='card-missao__pontos'>");
			        					out.println("<i class='icon icon-star'></i>");
			        					out.println("<span>"+m.getPontos()+" estrelas</span>");
			        				out.println("</div>");
			        			out.println("</div>");
		        			out.println("</div>");    			
		        		}
		        		
	                %>
	      </div>
	    </div>
	
	    <div class="perfil__cores">
	      <div class="form-control">
	        <label for="" class="form-label">Cor do perfil</label>
	        <div class="radio-cores">
	          <div class="radio-cores__row">
	          	<input type="radio" checked="checked" hidden value="<% out.print(tema); %>" name="cor" />
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-success-dark">
	              <input type="radio" value="#00b894" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-success-light ">
	              <input type="radio" value="#00cec9" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-success">
	              <input type="radio" value="#55efc4" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-primary-light">
	              <input type="radio" value="#74b9ff" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-primary">
	              <input type="radio" value="#0984e3" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	          </div>
	          <div class="radio-cores__row">
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-danger">
	              <input type="radio" value="#ff7675" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-pink  
	        jsRadioInput ">
	              <input type="radio" value="#fd79a8" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-pink-dark">
	              <input type="radio" value="#e84393" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-purple">
	              <input type="radio" value="#a29bfe" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	            <div tabindex="0" class="radio__cor jsOpcaoCor bg-purple-dark">
	              <input type="radio" value="#6c5ce7" class="radio-cores__input jsRadioInput" name="cor">
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
	
	    <div class="perfil__acessos">
	      <div class="form">
	        <div class="perfil-section-title">
	          <h3>Acessos</h3>
	        </div>
	        <div class="form-control">
	          <label for="" class="form-label">E-mail</label>
	          <input type="email" placeholder="Ex.: geo@mail.com" name="email" class="text-input completo jsCompletoQuandoSemFoco"
	            value="<% out.println(email); %>">
	        </div>
	        <div class="form-control">
	          <label for="" class="form-label">Senha</label>
	          <input type="password" placeholder="Ex.: ********" name="senha" class="text-input completo jsCompletoQuandoSemFoco"
	            value="<% out.print(senha); %>">
	        </div>
	      </div>
	    </div>
	
	    <div class="perfil__footer">
	      <div class="perfil__salvar">
	        <button type="submit" class="button--primary button--xs-small jsToggleModoEdicao">
	          Salvar
	        </button>
	      </div>
	      <div class="editar-perfil">
	        <div class="editar-perfil__icone"></div>
	        <div class="editar-perfil__texto jsToggleModoEdicao">
	          editar perfil
	        </div>
	      </div>
		  <div class="form centralizado zona-de-perigo">
		    <div class="perfil-section-title form__section-title">
		      <h3>�rea de perigo</h3>
		    </div>
		    <a class="button--danger button--small jsToggleModoEdicao">
		      Deletar perfil
		    </a>
		  </div>
	    </div>
	  </div>
  </form>

  <div class="missao-overlay jsOverlayMissao" data-cod="a">
    <div class="modal-missao jsModalMissao">
      <button class="modal-missao__close jsModalClose">
        <svg width="27" height="27" viewBox="0 0 27 27" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M13.5 1.5C6.873 1.5 1.5 6.873 1.5 13.5C1.5 20.127 6.873 25.5 13.5 25.5C20.127 25.5 25.5 20.127 25.5 13.5C25.5 6.873 20.127 1.5 13.5 1.5ZM13.5 22.5C8.5365 22.5 4.5 18.4635 4.5 13.5C4.5 8.5365 8.5365 4.5 13.5 4.5C18.4635 4.5 22.5 8.5365 22.5 13.5C22.5 18.4635 18.4635 22.5 13.5 22.5Z"
            fill="#2D3436" />
          <path
            d="M17.5829 9.43801L17.5619 9.41701C17.0054 8.86051 16.1024 8.86051 15.5459 9.41701L13.4999 11.463L11.4539 9.41701C10.8974 8.86051 9.99439 8.86051 9.43789 9.41701L9.41689 9.43801C8.86039 9.99451 8.86039 10.8975 9.41689 11.454L11.4629 13.5L9.41689 15.546C8.86039 16.1025 8.86039 17.0055 9.41689 17.562L9.43789 17.583C9.99439 18.1395 10.8974 18.1395 11.4539 17.583L13.4999 15.537L15.5459 17.583C16.1024 18.1395 17.0054 18.1395 17.5619 17.583L17.5829 17.562C18.1394 17.0055 18.1394 16.1025 17.5829 15.546L15.5369 13.5L17.5829 11.454C18.1394 10.8975 18.1394 9.99451 17.5829 9.43801Z"
            fill="#2D3436" />
        </svg>
      </button>
      <div class="modal-missao__header jsModalCorMissao">
        <div class="modal-missao__header-image">
          <img class="jsModalImagem" src="./assets/img/missao-escova-dental.png" alt="Imagem da miss�o">
        </div>
      </div>
      <div class="modal-missao__body">
        <div class="modal-missao__body-container">
          <div class="missao__body-title">
            <h2 class="jsModalTitulo">
              Escove os dentes
            </h2>
          </div>
          <div class="missao__body-points">
            <span class="missao__body-points-icone"></span>
            <span class="missao__body-points-text">
              <span class="jsModalEstrelas">
                200
              </span>
              <span>
                estrelas
              </span>
            </span>
          </div>
          <div class="missao__body-descricao">
            <article class="jsModalDescricao">
              <p>
                Escove os dentes durante o dia e ganhe pontos
              </p>
            </article>
          </div>
        </div>
      </div>
      <div class="modal-missao__footer">
        <a href="./ConcluiMissao" class="button button--primary button--xs-small jsConcluiMissao jsModalCorMissao">
          Conclu�da
        </a>
        <a  href="editar-missao.jsp" class="editar-missao jsEditarMissao">
            <div class="editar-missao__icone"></div>
            <div class="editar-missao__texto">
                editar miss�o
            </div>
        </a>
      </div>
    </div>
  </div>


  <script src="./assets/js/main.js"></script>
  <script src="./assets/js/barra-navegacao.js"></script>
  <script src="./assets/js/abas.js"></script>
  <script src="./assets/js/modal.js"></script>
  <script src="./assets/js/perfil.js"></script>
  <script src="./assets/js/inputs-de-texto.js"></script>
</body>

</html>