import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import examenService from '../services/examen.service';
import './Formularios.css';

const ConfirmarAsignacion: React.FC = () => {
  const [plantillas, setPlantillas] = useState<any[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    examenService.getBorradores().then(res => setPlantillas(res.data));
  }, []);

  const handleConfirmar = async () => {
    try {
      await examenService.asignarExamenes();
      alert('Exámenes asignados correctamente');
      navigate('/dashboard');
    } catch (err) {
      alert('Error al asignar exámenes');
    }
  };

  return (
    <div className="form-container">
      <h2>Confirmar Asignación</h2>
      <p style={{ textAlign: 'center', marginBottom: '2rem' }}>
        Se generarán {plantillas.length} exámenes. ¿Deseas proceder?
      </p>
      <div className="form-actions">
        <button className="btn btn-danger" onClick={() => navigate('/examenes/generar')}>Cancelar</button>
        <button className="btn btn-primary" onClick={handleConfirmar}>Confirmar Asignación</button>
      </div>
    </div>
  );
};

export default ConfirmarAsignacion;
