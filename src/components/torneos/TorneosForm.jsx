
export const TorneosForm = () => {
  return (
    <div className="row justify-content-center mt-5">
        <div className="col-lg-7">
            <div className="card shadow-lg border-0 rounded-lg mt-5">
                <div className="card-header">
                    <h4 className="text-center font-weight-light my-4"> Crear un Nuevo Torneo </h4>
                </div>
                <div className="card-body">
                    <form action="">
                        <div className="row ">
                            <div className="mb-3 col-md-6">
                                <label htmlFor="username" className="form-label">Nombre de usuario</label>
                                <input type="text" className="form-control" id="username" name="username" value="#"/>
                            </div>

                            <div className="mb-3 col-md-6">
                                <label htmlFor="name" className="form-label">Nombre</label>
                                <input type="text" className="form-control" id="name" name="name"
                                value=""/>

                            </div>
                        </div>

                        <div className="row">
                            <div className="mb-3 col-md-6">
                                <label htmlFor="email" className="form-label">Email</label>
                                <input type="text" className="form-control" id="email" name="email"
                                value="#"/>

                            </div>

                        </div>

                            <div className="row">
                                <div className="mb-3 col-md-6">
                                    <label htmlFor="password" className="form-label">Contraseña</label>
                                    <input type="password" className="form-control" id="password" name="password"
                                        value="#"/>
                                </div>

                                <div className="mb-3 col-md-6">
                                    <label htmlFor="confirm_password" className="form-label">Confirmar Contraseña</label>
                                    <input type="password" className="form-control" id="confirm_password" name="confirm_password"
                                        value="#"/>
                                </div>
                            </div>

                            <button type="submit" className="btn btn-primary">Registrar</button>
                    </form>



                </div>
            </div>
        </div>
    </div>

  )
}
