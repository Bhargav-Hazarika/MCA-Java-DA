from flask import Blueprint, request, jsonify
import pickle
import os
import numpy as np
import pandas as pd

predict_bp = Blueprint("predict", __name__)

# Load model
model_path = os.path.join(os.path.dirname(__file__), 'rfr_reg.pkl')

try:
    with open(model_path, "rb") as f:
        model = pickle.load(f)
except FileNotFoundError:
    model = None
    print("Error: The 'rfr_reg.pkl' model file was not found.")


FEATURE_COLUMNS = [
    "room_private",
    "person_capacity",
    "host_is_superhost",
    "multi",
    "biz",
    "cleanliness_rating",
    "guest_satisfaction_overall",
    "bedrooms",
    "dist",
    "metro_dist",
    "attr_index_norm",
    "rest_index_norm",
    "time_of_week",
    "price_per_person",
    "city_Amsterdam",
    "city_Athens",
    "city_Barcelona",
    "city_Berlin",
    "city_Budapest",
    "city_Lisbon",
    "city_London",
    "city_Paris",
    "city_Rome",
    "city_Vienna"
]


def encode_city(city_input):
    cities = ['Amsterdam', 'Athens', 'Barcelona', 'Berlin',
              'Budapest', 'Lisbon', 'London', 'Paris', 'Rome', 'Vienna']

    city_vector = [0] * len(cities)

    if city_input:
        city_input_clean = city_input.strip().title()
        if city_input_clean in cities:
            city_vector[cities.index(city_input_clean)] = 1

    return city_vector


@predict_bp.route("/predict", methods=["POST"])
def predict():
    if not model:
        return jsonify({"error": "Model not loaded"}), 500

    try:
        data = request.get_json()

        # --- Transformations ---
        guest_input = float(data.get("guest_satisfaction_overall", 0))
        guest_lambda = 0.184986605
        trans_guest = 100 + 1 - guest_input
        trans_guest = (trans_guest ** guest_lambda - 1) / guest_lambda

        trans_dist = np.log1p(float(data.get("dist", 0)))
        trans_metro = np.log1p(float(data.get("metro_dist", 0)))
        trans_ppp = np.log1p(float(data.get("price_per_person", 0)))
        trans_attr = np.log1p(float(data.get("attr_index_norm", 0)))
        trans_rest = np.log1p(float(data.get("rest_index_norm", 0)))

        # --- Base features ---
        features = [
            int(data.get("room_private", 0)),
            float(data.get("person_capacity", 2.0)),
            int(data.get("host_is_superhost", 0)),
            int(data.get("multi", 0)),
            int(data.get("biz", 0)),
            float(data.get("cleanliness_rating", 0)),
            trans_guest,
            float(data.get("bedrooms", 0)),
            trans_dist,
            trans_metro,
            trans_attr,
            trans_rest,
            int(data.get("time_of_week", 0)),
            trans_ppp
        ]

        # --- City encoding ---
        city_vector = encode_city(data.get("city", ""))
        features.extend(city_vector)

        # Convert to DataFrame (safer for sklearn)
        df = pd.DataFrame([features], columns=FEATURE_COLUMNS)

        prediction = model.predict(df)[0]

        return jsonify({
            "prediction": float(prediction)
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 400