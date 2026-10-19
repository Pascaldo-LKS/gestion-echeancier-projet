<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - Gestion d'Échéancier</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body class="login-body">

    <div class="login-container">
        <h2>Connexion</h2>
        <form action="LoginServlet" method="POST">
            <div class="form-group">
                <label>Nom d'utilisateur :</label>
                <input type="text" name="nom_complet" required placeholder="NAGAN">
            </div>
            
            <div class="form-group">
                <label>Mot de passe :</label>
                <input type="password" name="password" required placeholder="••••••••">
            </div>
            
            <button type="submit" class="btn-submit">Se Connecter</button>
        </form>
    </div>

</body>
</html>