angular.module("forCodeConfig").value("config", {
	
	baseUrl: function() {
		
		var _externalUrl = "http://187.59.123.244";
		var _internalUrl = "http://127.0.0.1";
		var _port = "8080";
		var _context = "/ForCode_SERVICE"

		var url = location.href;
		
		if (url.indexOf(_externalUrl) >= 0){
			return _externalUrl + ":" + _port + _context;
		} else {
			return _internalUrl + ":" + _port + _context;
		}

	},

	getError: function(code){
		
		var index = 0;

		var errors = [{code: index++, message: "Nome de usuário já existente"},
					  {code: index++, message: "Email já existente"},
					  {code: index++, message: "Usuário incorreto"},
					  {code: index++, message: "Instituição não cadastrada"},
					  {code: index++, message: "Senha incorreta"},
					  {code: index++, message: "User Contest inexistente"},
					  {code: index++, message: "Este contest não existe"},
					  {code: index++, message: "User Contest already registered"},
					  {code: index++, message: "Instituição já registrada"},
					  {code: index++, message: "Problema já foi usado em um ou mais contests"},
					  {code: index++, message: "Arquivo muito grande pra a tal requisição"},
					  {code: index++, message: "Error enquanto tentava deletar dados, por favor contate o administrador do server"}]

		for (var i = errors.length - 1; i >= 0; i--) {
			if (errors[i].code === code)
				return errors[i];
		};

	}
})