import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createAlumno } from '../services/alumno.service';
import { getGrados } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { ArrowLeft, Save, User } from 'lucide-react';

const AlumnoCreate: React.FC = () => {
  const [alumno, setAlumno] = useState({
    niu: '',
    nombre: '',
    apellidos: '',
    gradoId: 0,
  });
  const [grados, setGrados] = useState<Grado[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [loadingGrados, setLoadingGrados] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchGrados();
  }, []);

  const fetchGrados = async () => {
    try {
      const response = await getGrados();
      setGrados(response.data);
      setLoadingGrados(false);
    } catch (err) {
      setError('Error al cargar la lista de grados.');
      setLoadingGrados(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setAlumno(prev => ({ 
      ...prev, 
      [name]: name === 'gradoId' ? parseInt(value) : value 
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (alumno.gradoId === 0) {
      setError('Debe seleccionar un grado.');
      return;
    }

    setLoading(true);
    setError('');

    try {
      await createAlumno(alumno);
      navigate('/alumnos');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear el alumno. Verifique el NIU.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-4 md:p-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex items-center gap-4 mb-8">
          <button 
            onClick={() => navigate('/alumnos')}
            className="p-2 hover:bg-gray-200 rounded-full transition-colors"
          >
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-3xl font-bold text-gray-800">Añadir Nuevo Alumno</h1>
        </div>

        <div className="bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
          <div className="p-6 bg-blue-600 text-white flex items-center gap-3">
            <User size={24} />
            <h2 className="text-xl font-semibold">Datos del Alumno</h2>
          </div>

          <form onSubmit={handleSubmit} className="p-6 space-y-6">
            {error && (
              <div className="bg-red-50 text-red-600 p-4 rounded-lg border border-red-100 text-sm">
                {error}
              </div>
            )}

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">DNI</label>
                <input
                  type="text"
                  name="dni"
                  required
                  pattern="^([XYZxyz]\d{7}[A-Za-z]|\d{8}[A-Za-z])$"
                  title="Formato inválido: 8 dígitos y 1 letra (DNI) o X,Y,Z seguido de 7 dígitos y 1 letra (NIE)"
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                  value={alumno.dni}
                  onChange={handleChange}
                  placeholder="Ej: 12345678X o X1234567A"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Nombre</label>
                <input
                  type="text"
                  name="nombre"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                  value={alumno.nombre}
                  onChange={handleChange}
                />
              </div>

              <div className="md:col-span-2">
                <label className="block text-sm font-medium text-gray-700 mb-2">Apellidos</label>
                <input
                  type="text"
                  name="apellidos"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                  value={alumno.apellidos}
                  onChange={handleChange}
                />
              </div>

              <div className="md:col-span-2">
                <label className="block text-sm font-medium text-gray-700 mb-2">Grado</label>
                <select
                  name="gradoId"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none bg-white"
                  value={alumno.gradoId}
                  onChange={handleChange}
                  disabled={loadingGrados}
                >
                  <option value={0}>Seleccione un grado...</option>
                  {grados.map(grado => (
                    <option key={grado.id} value={grado.id}>
                      [{grado.codigo}] {grado.titulo}
                    </option>
                  ))}
                </select>
                {loadingGrados && <p className="text-xs text-gray-500 mt-1">Cargando grados...</p>}
              </div>
            </div>

            <div className="flex justify-end pt-4">
              <button
                type="submit"
                disabled={loading || loadingGrados}
                className="flex items-center gap-2 bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors shadow-md disabled:bg-blue-400"
              >
                <Save size={20} />
                <span>{loading ? 'Guardando...' : 'Guardar Alumno'}</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AlumnoCreate;
